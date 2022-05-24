package com.heymaster.heymaster.ui.master.profile

import com.heymaster.heymaster.data.network.ApiService

class MasterProfileRepository(private val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
    suspend fun getImage(id: Int) = apiService.getImage(id)


}