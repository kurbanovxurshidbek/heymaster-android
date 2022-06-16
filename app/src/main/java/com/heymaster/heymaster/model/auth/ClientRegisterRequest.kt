package com.heymaster.heymaster.model.auth

import com.heymaster.heymaster.model.Device

data class ClientRegisterRequest(
    val Date: String?,
    val fullName: String?,
    val gender: Boolean?,
    val phoneNumber: String?,
    val password: String?,
    val deviceId: String?,
    val deviceLan: String?

)