package com.heymaster.heymaster.role.master.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.role.master.repository.MasterPortfolioRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel

class MasterPortfolioViewModelFactory(private val repository: MasterPortfolioRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterProfileViewModel::class.java)){
            return MasterProfileViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}