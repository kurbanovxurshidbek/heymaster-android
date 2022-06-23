package com.heymaster.heymaster.model.auth

data class ClientToMasterResponse(
    val message: String,
    val `object`: Any,
    val success: Boolean
)