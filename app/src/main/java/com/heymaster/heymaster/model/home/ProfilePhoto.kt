package com.heymaster.heymaster.model.home

data class ProfilePhoto(
    val contentType: String,
    val fileOriginalName: String,
    val id: Int,
    val name: Any,
    val profilePhoto: Boolean,
    val size: Int
)