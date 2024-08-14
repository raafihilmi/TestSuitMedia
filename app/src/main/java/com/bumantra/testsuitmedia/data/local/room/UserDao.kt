package com.bumantra.testsuitmedia.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bumantra.testsuitmedia.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<User>)

    @Query("SELECT * FROM user")
    fun getUsers(): PagingSource<Int, User>

    @Query("DELETE FROM user")
    suspend fun clearAllUser()
}