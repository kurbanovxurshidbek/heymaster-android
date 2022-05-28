package com.heymaster.heymaster.ui.user.booking

import com.heymaster.heymaster.data.network.ApiService

class UserBookingRepository(
    private val apiService: ApiService
) {
    suspend fun getServices() = apiService.getServices()
}