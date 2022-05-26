package com.heymaster.heymaster.ui.master.notification.suggestion

import com.heymaster.heymaster.data.network.ApiService

class NotificationSuggestionRepository(
    private val apiService: ApiService
) {

    suspend fun getNotificationSuggestion() = apiService.getNotification()

}