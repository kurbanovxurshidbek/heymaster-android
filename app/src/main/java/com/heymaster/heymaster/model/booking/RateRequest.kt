package com.heymaster.heymaster.model.booking

import android.media.Rating

data class RateRequest(
    val toWhomId: Int,
    val rating: Int,
    val feedback: String

)

//"toWhomId": 0,
//"rating": 0,
//"feedback": "string"
