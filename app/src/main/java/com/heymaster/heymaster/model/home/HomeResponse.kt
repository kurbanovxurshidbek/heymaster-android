package com.heymaster.heymaster.model.home

import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.model.auth.Profession

data class HomeResponse(
    val advertisingList: List<Advertising>,
    val categoryList: List<Category>,
    val topMastersList: List<TopMasters>,
    val topProfessionList: List<Object>
)

