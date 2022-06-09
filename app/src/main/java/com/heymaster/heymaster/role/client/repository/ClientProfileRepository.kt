package com.heymaster.heymaster.role.client.repository

import com.heymaster.heymaster.data.network.ApiService

class ClientProfileRepository(
    private val apiService: ApiService
) {

    suspend fun currentUser() = apiService.currentUser()
}