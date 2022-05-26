package com.heymaster.heymaster.ui.master.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MasterPortfolioViewModelFactory(private val repository: MasterPortfolioRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterPortfolioViewModel::class.java)){
            return MasterPortfolioViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}