package com.heymaster.heymaster.model

import java.io.Serializable

data class User(
    val id: Int? = null,
    val fullName: String? = null,
    val gender: String? = null,
    val isMaster: Boolean? = null,
    val rate: Double? = null
)
