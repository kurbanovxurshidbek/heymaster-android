package com.heymaster.heymaster.model.auth

data class ClientRegisterRequest(
    val Date: String?,
    val fullName: String?,
    val gender: Boolean?,
    val phoneNumber: String?
)