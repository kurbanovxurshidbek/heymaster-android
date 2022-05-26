package com.heymaster.heymaster.ui.master.deatailspage.fordetails

import com.heymaster.heymaster.data.network.ApiService

class DetailsRepository(private  val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()


}