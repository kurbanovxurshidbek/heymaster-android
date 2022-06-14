package com.heymaster.heymaster.model.auth

import com.heymaster.heymaster.model.home.Category

data class Object(
    val category: Category,
    val id: Int,
    val isActive: Boolean,
    val name: String
)