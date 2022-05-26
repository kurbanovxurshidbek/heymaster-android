package com.heymaster.heymaster.ui.master.notification.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotificationMessagesViewModelFactory(private val repository: NotificationMessagesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationMessagesViewModel::class.java)){
            return NotificationMessagesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }


}