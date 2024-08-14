package com.bumantra.testsuitmedia.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumantra.testsuitmedia.data.local.entity.RemoteKeys
import com.bumantra.testsuitmedia.data.local.entity.User
import com.bumantra.testsuitmedia.data.local.room.UsersDatabase
import com.bumantra.testsuitmedia.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator(
    private val userDatabase: UsersDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, User>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (userDatabase.getRemoteKeys().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        try {
            val responseData = apiService.getUsers(page, state.config.pageSize)

            val user = responseData.data
            val endOfPage = user.isEmpty()

            userDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDatabase.getRemoteKeys().clearRemoteKeys()
                    userDatabase.getUsersDao().clearAllUser()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPage) null else page.plus(1)
                val keys = user.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey, currentPage = page)
                }
                userDatabase.getRemoteKeys().insertAll(keys)
                userDatabase.getUsersDao().insertAll(user)
            }
            return MediatorResult.Success(endOfPage)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, User>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userDatabase.getRemoteKeys().getRemoteKeyByUserID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { user ->
            userDatabase.getRemoteKeys().getRemoteKeyByUserID(user.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { user ->
            userDatabase.getRemoteKeys().getRemoteKeyByUserID(user.id)
        }
    }
}