package com.heymaster.heymaster.ui.auth

import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.model.auth.*

class AuthRepository(
    private val apiService: ApiService
) {

    suspend fun login(phoneNumber: LoginRequest) = apiService.login(phoneNumber)

    suspend fun confirm(confirmRequest: ConfirmRequest) = apiService.confirm(confirmRequest)

    suspend fun clientRegister(clientRegisterRequest: ClientRegisterRequest) = apiService.clientRegister(clientRegisterRequest)

    suspend fun masterRegister(masterRegisterRequest: MasterRegisterRequest) = apiService.masterRegister(masterRegisterRequest)

    suspend fun getDistricts() = apiService.getDistricts()
}