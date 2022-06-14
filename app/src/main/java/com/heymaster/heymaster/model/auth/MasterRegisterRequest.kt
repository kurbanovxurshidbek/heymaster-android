package com.heymaster.heymaster.model.auth

data class MasterRegisterRequest(
    val birthDate: String? = null,
    val districtId: Int? = null,
    val experienceYear: Int? = null,
    val fullName: String? = null,
    val gender: Boolean? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val professionIdList: List<Int>? = null,
    val regionId: Int? = null,
    val salary: Int? = null,
    val deviceId: String? = null,
    val deviceLan: String? = null
)