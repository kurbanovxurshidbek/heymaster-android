package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService

class MasterBookingRepository(
    val apiService: ApiService
) {

    suspend fun getMasterActiveBooking() = apiService.getMasterActiveBooking()

    suspend fun getMasterHistoryBooking() = apiService.getBookingMasterHistory()
}