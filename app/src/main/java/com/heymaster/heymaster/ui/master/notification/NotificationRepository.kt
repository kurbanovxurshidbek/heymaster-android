package com.heymaster.heymaster.ui.master.notification

import com.heymaster.heymaster.data.network.ApiService

class NotificationRepository(
    private val apiService: ApiService
) {

    suspend fun getNotificationSuggestion() = apiService.getNotification()

}