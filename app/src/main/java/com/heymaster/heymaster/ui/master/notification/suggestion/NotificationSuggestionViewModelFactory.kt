package com.heymaster.heymaster.ui.master.notification.suggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotificationSuggestionViewModelFactory(private val repository: NotificationSuggestionRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationSuggestionViewModel::class.java)){
            return NotificationSuggestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }


}