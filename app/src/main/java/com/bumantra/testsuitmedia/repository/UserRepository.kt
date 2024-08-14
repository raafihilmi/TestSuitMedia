package com.bumantra.testsuitmedia.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.bumantra.testsuitmedia.data.UsersRemoteMediator
import com.bumantra.testsuitmedia.data.local.entity.User
import com.bumantra.testsuitmedia.data.local.room.UsersDatabase
import com.bumantra.testsuitmedia.data.remote.retrofit.ApiService

class UserRepository private constructor(
    private val userDatabase: UsersDatabase,
    private val apiService: ApiService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllUser(): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = UsersRemoteMediator(userDatabase,apiService),
            pagingSourceFactory = {
                userDatabase.getUsersDao().getUsers()
            }
        ).liveData
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userDatabase: UsersDatabase, apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDatabase, apiService)
            }
    }
}