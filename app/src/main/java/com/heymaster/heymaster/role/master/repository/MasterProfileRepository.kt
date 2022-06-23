package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService
import okhttp3.RequestBody

class MasterProfileRepository(private val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
    suspend fun getImage(id: Int) = apiService.getImage(id)

    suspend fun getMasterProfileInfo() = apiService.currentUser()

    suspend fun uploadAttachment(body: RequestBody) = apiService.uploadAttachment(body)

    suspend fun attachmentInfo() = apiService.attachmentInfo()

    suspend fun masterToClient() = apiService.masterToClient()



    suspend fun getRegions() = apiService.getRegions()

    suspend fun getDistrictsFromRegion(id: Int) = apiService.getDistrictsFromRegion(id)

    suspend fun getProfessions() = apiService.getProfessions()


}