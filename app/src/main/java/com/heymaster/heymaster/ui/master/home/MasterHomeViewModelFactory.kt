package com.heymaster.heymaster.ui.master.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.ui.user.home.UserHomeRepository
import com.heymaster.heymaster.ui.user.home.UserHomeViewModel

class MasterHomeViewModelFactory(private val repository: MasterHomeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterHomeViewModel::class.java)) {
            return MasterHomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }

}