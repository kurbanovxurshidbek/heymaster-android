package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class MasterHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getServices() = apiService.getServices()

    suspend fun getService(id: Int) = apiService.getService(id)

    suspend fun getAds() = apiService.getAds()
    suspend fun getHome() = apiService.getHome()

    suspend fun getProfessionFromCategory(id: String) = apiService.getProfessionFromCategory(id)

    suspend fun getMasterFromProfessionId(id: Int) = apiService.getMasterFromProfession(id)

    suspend fun getActiveMasters() = apiService.getActiveMasters()

    suspend fun getAllCategory() = apiService.getAllCategory()

    suspend fun getProfessions() = apiService.getProfessions()

    suspend fun getNotifications() = apiService.getNotifications()
}