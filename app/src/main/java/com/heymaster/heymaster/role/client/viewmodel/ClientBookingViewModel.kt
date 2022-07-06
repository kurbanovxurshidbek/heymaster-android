package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.booking.*
import com.heymaster.heymaster.role.client.repository.ClientBookingRepository
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class  ClientBookingViewModel(
    private val repository: ClientBookingRepository,
) : ViewModel() {

    private val _booking = MutableStateFlow<UiStateObject<BookingResponse>>(UiStateObject.EMPTY)
    val booking = _booking


    private val _activeBooking = MutableStateFlow<UiStateObject<ClientActiveBooking>>(UiStateObject.EMPTY)
    val activeBooking = _activeBooking

    private val _historyBooking = MutableStateFlow<UiStateObject<ClientHistoryBooking>>(UiStateObject.EMPTY)
    val historyBooking = _historyBooking


    private val _rate = MutableStateFlow<UiStateObject<RateResponse>>(UiStateObject.EMPTY)
    val rate = _rate


    fun booking(id: Int) = viewModelScope.launch {
        _booking.value = UiStateObject.LOADING

        try {
            val response = repository.booking(id)
            _booking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            UiStateObject.ERROR(e.userMessage())
        }
    }


    fun getClientActiveBooking() = viewModelScope.launch {
        _activeBooking.value = UiStateObject.LOADING

        try {
            val response = repository.getClientActiveBooking()
            _activeBooking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _activeBooking.value = UiStateObject.ERROR(e.userMessage())
        }
    }


    fun getClientHistoryBooking() = viewModelScope.launch {
        _historyBooking.value = UiStateObject.LOADING

        try {
            val response = repository.getClientHistoryBooking()
            _historyBooking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _historyBooking.value = UiStateObject.ERROR(e.userMessage())
        }
    }


    fun rateBooking(rateRequest: RateRequest) = viewModelScope.launch {
        _rate.value = UiStateObject.LOADING

        try {

            val response = repository.rateBooking(rateRequest)
            _rate.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _rate.value = UiStateObject.ERROR(e.userMessage())
        }
    }



}