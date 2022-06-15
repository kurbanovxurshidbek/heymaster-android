package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class DetailsRepository(private  val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
    suspend fun getMasterDetailInfo() = apiService.currentUser()


}