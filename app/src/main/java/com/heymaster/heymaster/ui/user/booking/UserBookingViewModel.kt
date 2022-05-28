package com.heymaster.heymaster.ui.user.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserBookingViewModel(
    private val repository: UserBookingRepository
    ): ViewModel() {

    private val _services = MutableStateFlow<UiStateList<Service>>(UiStateList.EMPTY)
    val services = _services

    fun getServices() = viewModelScope.launch {
        _services.value = UiStateList.LOADING
        try {
            val response = repository.getServices()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _services.value = UiStateList.ERROR(error.errorMessage)
            } else {
                _services.value = UiStateList.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _services.value = UiStateList.ERROR(e.userMessage())
        }
    }
}