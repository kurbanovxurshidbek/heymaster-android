package com.heymaster.heymaster.ui.master.notification.messages

import com.heymaster.heymaster.data.network.ApiService

class NotificationMessagesRepository(
    private val apiService: ApiService
) {

    suspend fun getNotificationSuggestion() = apiService.getNotification()

}