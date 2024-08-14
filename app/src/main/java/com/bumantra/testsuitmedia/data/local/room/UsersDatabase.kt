package com.bumantra.testsuitmedia.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumantra.testsuitmedia.data.local.entity.RemoteKeys
import com.bumantra.testsuitmedia.data.local.entity.User

@Database(
    entities = [User::class, RemoteKeys::class],
    version = 1
)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun getUsersDao(): UserDao
    abstract fun getRemoteKeys(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UsersDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java, "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}