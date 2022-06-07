package com.heymaster.heymaster.model.auth

data class ConfirmResponse(
    val message: String,
    val `object`: Any,
    val success: Boolean
)