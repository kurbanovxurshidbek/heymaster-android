package com.heymaster.heymaster.model.home

data class NotificationResponse(
    val message: String,
    val `object`: List<ObjectX>,
    val success: Boolean
)