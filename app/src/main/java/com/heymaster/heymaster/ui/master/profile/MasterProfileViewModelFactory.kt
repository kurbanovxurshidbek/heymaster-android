package com.heymaster.heymaster.ui.master.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MasterProfileViewModelFactory(private val repository: MasterProfileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterProfileViewModel::class.java)){
            return MasterProfileViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}