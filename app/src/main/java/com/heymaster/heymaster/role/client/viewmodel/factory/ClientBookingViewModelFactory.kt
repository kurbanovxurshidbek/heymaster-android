package com.heymaster.heymaster.role.client.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingViewModel

class ClientBookingViewModelFactory(private val repository: ClientBookingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientBookingViewModel::class.java)){
            return ClientBookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}