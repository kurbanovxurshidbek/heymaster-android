package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class MasterPortfolioRepository(private val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
    suspend fun getImage(id: Int) = apiService.getImage(id)


}