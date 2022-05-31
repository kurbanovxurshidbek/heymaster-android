package com.heymaster.heymaster.role.master.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.master.repository.MasterNotificationRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterNotificationViewModel

class MasterNotificationViewModelFactory(private val repository: MasterNotificationRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterNotificationViewModel::class.java)){
            return MasterNotificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }


}