package com.heymaster.heymaster.model.booking

data class Profession(
    val category: Category,
    val id: Int,
    val isActive: Boolean,
    val name: String
)