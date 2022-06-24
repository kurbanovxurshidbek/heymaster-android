package com.heymaster.heymaster.model.booking

import com.heymaster.heymaster.model.auth.CurrentUser

data class Object(
    val accepted: Boolean,
    val createAt: String,
    val createdBy: Int,
    val id: Int,
    val isFinished: Boolean,
    val toWhom: ToWhom,
    val updateAt: String,
    val updatedBy: Int,
    val from: From
)