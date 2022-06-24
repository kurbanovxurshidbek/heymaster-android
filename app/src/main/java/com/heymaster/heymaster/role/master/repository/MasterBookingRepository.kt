package com.heymaster.heymaster.role.master.repository

import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.model.booking.BookingAcceptRequest

class MasterBookingRepository(
    val apiService: ApiService
) {

    suspend fun getMasterActiveBooking() = apiService.getMasterActiveBooking()

    suspend fun getMasterHistoryBooking() = apiService.getBookingMasterHistory()

    suspend fun bookingAcceptOrCancel(bookingAcceptRequest: BookingAcceptRequest) = apiService.bookingAcceptOrCancel(bookingAcceptRequest)
}