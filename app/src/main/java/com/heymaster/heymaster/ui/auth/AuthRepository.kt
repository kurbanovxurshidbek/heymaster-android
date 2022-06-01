package com.heymaster.heymaster.ui.auth

import com.heymaster.heymaster.data.network.ApiService

class AuthRepository(
    private val apiService: ApiService
) {

    suspend fun login(phoneNumber: String) = apiService.login()

    suspend fun confirm() = apiService.confirm()

    suspend fun sigUp() = apiService.signUp()
}