package com.bumantra.testsuitmedia.ui.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumantra.testsuitmedia.data.local.entity.User
import com.bumantra.testsuitmedia.repository.UserRepository

class UserViewModel(private val repository: UserRepository): ViewModel() {
    val getAllUser: LiveData<PagingData<User>> =
        repository.getAllUser().cachedIn(viewModelScope)
}