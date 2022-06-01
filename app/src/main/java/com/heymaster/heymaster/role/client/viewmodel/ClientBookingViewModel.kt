package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.role.client.repository.ClientBookingRepository
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ClientBookingViewModel(
    private val repository: ClientBookingRepository,
) : ViewModel() {

    private val _bookings = MutableStateFlow<UiStateList<Service>>(UiStateList.EMPTY)
    val bookings = _bookings

    fun getBookings() = viewModelScope.launch {
        _bookings.value = UiStateList.LOADING
        try {
            val response = repository.getBookings()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _bookings.value = UiStateList.ERROR(error.errorMessage)
            } else {
                _bookings.value = UiStateList.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _bookings.value = UiStateList.ERROR(e.userMessage())
        }
    }



}