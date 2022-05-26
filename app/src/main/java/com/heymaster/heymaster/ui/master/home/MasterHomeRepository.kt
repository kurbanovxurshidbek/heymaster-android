package com.heymaster.heymaster.ui.master.home

import com.heymaster.heymaster.data.network.ApiService

class MasterHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getServices() = apiService.getServices()

    suspend fun getService(id: Int) = apiService.getService(id)

    suspend fun getAds() = apiService.getAds()
}