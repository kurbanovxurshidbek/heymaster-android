package com.heymaster.heymaster.role.master.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heymaster.heymaster.model.booking.*
import com.heymaster.heymaster.role.master.repository.MasterBookingRepository
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MasterBookingViewModel(
    private val repository: MasterBookingRepository
): ViewModel() {

    private val _masterActiveBooking = MutableStateFlow<UiStateObject<MasterActiveBooking>>(UiStateObject.EMPTY)
    val masterActiveBooking = _masterActiveBooking

    fun getMasterActiveBooking() = viewModelScope.launch {
        _masterActiveBooking.value = UiStateObject.LOADING

        try {
            val response = repository.getMasterActiveBooking()
            _masterActiveBooking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _masterActiveBooking.value = UiStateObject.ERROR(e.userMessage())
        }
    }


    private val _masterHistoryBooking = MutableStateFlow<UiStateObject<MasterHistoryBooking>>(UiStateObject.EMPTY)
    val masterHistoryBooking = _masterHistoryBooking

    fun getMasterHistoryBooking() = viewModelScope.launch {
        _masterHistoryBooking.value = UiStateObject.LOADING

        try {
            val response = repository.getMasterHistoryBooking()
            _masterHistoryBooking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _masterHistoryBooking.value = UiStateObject.ERROR(e.userMessage())
        }
    }


    private val _bookingAcceptOrCancel = MutableStateFlow<UiStateObject<BookingAcceptResponse>>(UiStateObject.EMPTY)
    val bookingAcceptOrCancel = _bookingAcceptOrCancel

    fun bookingAcceptOrCancel(acceptRequest: BookingAcceptRequest) = viewModelScope.launch {
        _bookingAcceptOrCancel.value = UiStateObject.LOADING

        try {
            val response = repository.bookingAcceptOrCancel(acceptRequest)
            _bookingAcceptOrCancel.value = UiStateObject.SUCCESS(response.body()!!)
            Boolean

        } catch (e: Exception) {
            _bookingAcceptOrCancel.value = UiStateObject.ERROR(e.userMessage())
        }
    }


    private val _finish = MutableStateFlow<UiStateObject<BookingFinishResponse>>(UiStateObject.EMPTY)
    val finish = _finish


    fun bookingFinish(id: Int) = viewModelScope.launch {
        _finish.value = UiStateObject.LOADING

        try {
            val response = repository.bookingFinish(id)

            _finish.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _finish.value = UiStateObject.ERROR(e.userMessage())
        }
    }




}