package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ClientHomeViewModel(
    private val repository: ClientHomeRepository,
) : ViewModel() {



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


    private val _service = MutableStateFlow<UiStateObject<Service>>(UiStateObject.EMPTY)
    val service = _service

    fun getService(id: Int) = viewModelScope.launch {
        _service.value = UiStateObject.LOADING
        try {
            val response = repository.getService(id)
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _service.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _service.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _service.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    private val _ads = MutableStateFlow<UiStateList<Ads>>(UiStateList.EMPTY)
    val ads = _ads

    fun getAds() = viewModelScope.launch {
        _ads.value = UiStateList.LOADING

        try {
            val response = repository.getAds()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _ads.value = UiStateList.ERROR(error.errorMessage)
            } else {
                _ads.value = UiStateList.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _ads.value = UiStateList.ERROR(e.userMessage())
        }
    }


}

