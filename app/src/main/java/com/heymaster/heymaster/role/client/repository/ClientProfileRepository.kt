package com.heymaster.heymaster.role.client.repository

import com.heymaster.heymaster.data.network.ApiService
import okhttp3.RequestBody

class ClientProfileRepository(
    private val apiService: ApiService
) {

    suspend fun currentUser() = apiService.currentUser()

    suspend fun uploadAttachment(body: RequestBody) = apiService.uploadAttachment(body)

    suspend fun attachmentInfo() = apiService.attachmentInfo()
}