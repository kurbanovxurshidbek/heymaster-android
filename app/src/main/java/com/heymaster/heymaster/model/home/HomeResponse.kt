package com.heymaster.heymaster.model.home

data class HomeResponse(
    val advertisingList: List<Advertising>,
    val categoryList: List<Category>,
    val topMastersList: List<TopMasters>,
    val topProfessionList: List<Any>
)

