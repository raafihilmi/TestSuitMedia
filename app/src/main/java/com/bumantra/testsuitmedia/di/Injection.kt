package com.bumantra.testsuitmedia.di

import android.content.Context
import com.bumantra.testsuitmedia.data.local.room.UsersDatabase
import com.bumantra.testsuitmedia.data.remote.retrofit.ApiConfig
import com.bumantra.testsuitmedia.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val userDatabase = UsersDatabase.getDatabase(context)
        return UserRepository.getInstance(userDatabase, apiService)
    }
}