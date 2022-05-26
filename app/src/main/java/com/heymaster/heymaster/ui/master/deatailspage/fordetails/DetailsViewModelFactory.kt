package com.heymaster.heymaster.ui.master.deatailspage.fordetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModelFactory(private val repository: DetailsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}