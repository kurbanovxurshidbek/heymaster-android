package com.heymaster.heymaster.model

data class District(
    val id: Int,
    val nameRu: String,
    val nameUz: String,
    val region: Region
)

data class Region(
    val id: Int,
    val nameRu: String,
    val nameUz: String
)