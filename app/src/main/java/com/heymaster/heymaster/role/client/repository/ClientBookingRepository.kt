package com.heymaster.heymaster.role.client.repository

import com.heymaster.heymaster.data.network.ApiService

class ClientBookingRepository(
    private val apiService: ApiService
) {
    suspend fun getBookings() = apiService.getBookings()

    suspend fun booking(id: Int) = apiService.booking(id)


}