package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class DetailsRepository(private  val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()

    suspend fun getMasterDetailInfo(id: Int) = apiService.getMasterDetailInfo(id)

    suspend fun booking(id: Int) = apiService.booking(id)
}