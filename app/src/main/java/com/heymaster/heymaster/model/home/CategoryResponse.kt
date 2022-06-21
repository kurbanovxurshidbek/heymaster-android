package com.heymaster.heymaster.model.home

data class CategoryResponse(
    val message: String,
    val `object`: List<Category>,
    val success: Boolean
)