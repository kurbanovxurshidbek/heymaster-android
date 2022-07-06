package com.heymaster.heymaster.role.client.repository

import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.model.booking.RateRequest

class ClientBookingRepository(
    private val apiService: ApiService
) {

    suspend fun booking(id: Int) = apiService.booking(id)

    suspend fun getClientActiveBooking() = apiService.getClientActiveBooking()

    suspend fun getClientHistoryBooking() = apiService.getClientBookingHistory()

    suspend fun rateBooking(rateRequest: RateRequest) = apiService.bookingRate(rateRequest)




}