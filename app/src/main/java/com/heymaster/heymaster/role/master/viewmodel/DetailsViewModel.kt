package com.heymaster.heymaster.role.master.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.model.booking.BookingResponse
import com.heymaster.heymaster.model.home.HomeResponse
import com.heymaster.heymaster.model.masterdetail.MasterDetail
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel (private val repository: DetailsRepository): ViewModel(){

    private val _portfolio = MutableStateFlow<UiStateList<Portfolio>>(UiStateList.EMPTY)
    val portfolio = _portfolio

    
    private val _masterProfile = MutableStateFlow<UiStateObject<MasterDetail>>(UiStateObject.EMPTY)
    val masterProfile = _masterProfile

    fun getMasterDetail(id: Int) = viewModelScope.launch {
        _masterProfile.value = UiStateObject.LOADING
        try {
            val response = repository.getMasterDetailInfo(id)
            if (response.isSuccessful) {
                _masterProfile.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {

            _masterProfile.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    private val _booking = MutableStateFlow<UiStateObject<BookingResponse>>(UiStateObject.EMPTY)
    val booking = _booking


    fun booking(id: Int) = viewModelScope.launch {
        _booking.value = UiStateObject.LOADING


        try {
            val response = repository.booking(id)
            _booking.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            UiStateObject.ERROR(e.userMessage())
        }
    }

}