package com.heymaster.heymaster.ui.auth

import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.model.auth.ClientRegisterRequest
import com.heymaster.heymaster.model.auth.ClientRegisterResponse
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.LoginRequest

class AuthRepository(
    private val apiService: ApiService
) {

    suspend fun login(phoneNumber: LoginRequest) = apiService.login(phoneNumber)

    suspend fun confirm(confirmRequest: ConfirmRequest) = apiService.confirm(confirmRequest)

    suspend fun clientRegister(clientRegisterRequest: ClientRegisterRequest) = apiService.clientRegister(clientRegisterRequest)



    suspend fun currentUser() = apiService.currentUser()

    suspend fun sigUp() = apiService.signUp()
}