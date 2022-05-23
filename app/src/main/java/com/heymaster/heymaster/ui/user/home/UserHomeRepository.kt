package com.heymaster.heymaster.ui.user.home

import com.heymaster.heymaster.data.network.ApiService

class UserHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getServices() = apiService.getServices()

    suspend fun getService(id: Int) = apiService.getService(id)

    suspend fun getAds() = apiService.getAds()
}