package com.heymaster.heymaster.model.home

import com.heymaster.heymaster.model.Object

data class Advertising(
    val message: String,
    val `object`: List<Object>,
    val success: Boolean
)