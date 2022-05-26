package com.heymaster.heymaster.ui.user.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.model.User
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.user.home.UserHomeViewModel

class UserBookingViewModelFactory(private val repository: UserBookingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserBookingViewModel::class.java)){
            return UserBookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}