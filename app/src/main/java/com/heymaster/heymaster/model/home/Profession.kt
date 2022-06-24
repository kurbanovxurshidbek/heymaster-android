package com.heymaster.heymaster.model.home

data class Profession(
    val category: Category,
    val id: Int,
    val isActive: Boolean,
    val name: String,
    val photoUrl: String
)