package com.heymaster.heymaster.model.booking

data class Object(
    val accepted: Boolean,
    val createAt: String,
    val createdBy: Int,
    val id: Int,
    val isFinished: Boolean,
    val toWhom: ToWhom,
    val updateAt: String,
    val updatedBy: Int
)