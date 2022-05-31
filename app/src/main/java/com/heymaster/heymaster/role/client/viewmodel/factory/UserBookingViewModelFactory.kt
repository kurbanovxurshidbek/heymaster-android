package com.heymaster.heymaster.role.client.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.client.viewmodel.UserBookingRepository
import com.heymaster.heymaster.role.client.viewmodel.UserBookingViewModel

class UserBookingViewModelFactory(private val repository: UserBookingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserBookingViewModel::class.java)){
            return UserBookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}