package com.heymaster.heymaster.model.home

import com.heymaster.heymaster.model.auth.Object


data class HomeResponse(
    val advertisingList: List<Advertising>,
    val categoryList: List<Category>,
    val topMastersList: List<com.heymaster.heymaster.model.home.Object>,
    val topProfessionList: List<Object>,
)

