package com.heymaster.heymaster.role.client.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingViewModel
import com.heymaster.heymaster.role.client.viewmodel.ClientProfileViewModel

class ClientProfileViewModelFactory(private val repository: ClientProfileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientProfileViewModel::class.java)){
            return ClientProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}