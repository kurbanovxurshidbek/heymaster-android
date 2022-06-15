package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.home.Advertising
import com.heymaster.heymaster.model.home.HomeResponse
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ClientHomeViewModel(
    private val repository: ClientHomeRepository,
) : ViewModel() {



    private val _home = MutableStateFlow<UiStateObject<HomeResponse>>(UiStateObject.EMPTY)
    val home = _home

    fun getHome() = viewModelScope.launch {
        _home.value = UiStateObject.LOADING
        try {
            val response = repository.getHome()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _home.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _home.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _home.value = UiStateObject.ERROR(e.userMessage())
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

    private val _ads = MutableStateFlow<UiStateObject<Advertising>>(UiStateObject.EMPTY)
    val ads = _ads

    fun getAds() = viewModelScope.launch {
        _ads.value = UiStateObject.LOADING

        try {
            val response = repository.getAds()
            _ads.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _ads.value = UiStateObject.ERROR(e.userMessage())
        }
    }


}

