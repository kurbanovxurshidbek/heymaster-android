package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class MasterNotificationRepository(
    private val apiService: ApiService
) {

    suspend fun getNotificationSuggestion() = apiService.getNotification()

}