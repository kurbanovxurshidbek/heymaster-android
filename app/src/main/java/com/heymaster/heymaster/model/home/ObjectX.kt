package com.heymaster.heymaster.model.home

import com.heymaster.heymaster.model.booking.ToWhom

data class ObjectX(
    val body: String,
    val createAt: String,
    val createdBy: Int,
    val id: Int,
    val title: String,
    val toWhom: ToWhom,
    val updateAt: String,
    val updatedBy: Int
)