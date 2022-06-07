package com.heymaster.heymaster.model.auth

data class LoginResponse(
    val message: String,
    val `object`: Int,
    val success: Boolean
)