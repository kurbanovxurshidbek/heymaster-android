package com.heymaster.heymaster.model.auth

data class ConfirmRequest(
    val password: String,
    val phoneNumber: String
)