package com.heymaster.heymaster.ui.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserHomeViewModelFactory(private val repository: UserHomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserHomeViewModel::class.java)) {
            return UserHomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}