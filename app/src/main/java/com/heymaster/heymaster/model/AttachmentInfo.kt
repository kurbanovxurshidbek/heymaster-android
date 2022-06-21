package com.heymaster.heymaster.model

data class AttachmentInfo(
    val contentType: String,
    val fileOriginalName: String,
    val id: Int,
    val name: Any,
    val profilePhoto: Boolean,
    val size: Int
)