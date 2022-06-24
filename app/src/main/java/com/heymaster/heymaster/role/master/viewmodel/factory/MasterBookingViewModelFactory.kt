package com.heymaster.heymaster.role.master.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.master.repository.MasterBookingRepository
import com.heymaster.heymaster.role.master.repository.MasterProfileRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterBookingViewModel
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel

class MasterBookingViewModelFactory(private val repository: MasterBookingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterBookingViewModel::class.java)){
            return MasterBookingViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}