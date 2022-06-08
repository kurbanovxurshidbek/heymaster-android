package com.heymaster.heymaster.model

class Districts : ArrayList<DistrictItem>()

data class DistrictItem(
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