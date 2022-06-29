package com.heymaster.heymaster.model.editmasterprofile


data class EditMasterRequest (
    val fullName: String,
    val regionId: Int,
    val districtId: Int,
    val experienceYear: Int,
    val professionList: List<Int>,

    )