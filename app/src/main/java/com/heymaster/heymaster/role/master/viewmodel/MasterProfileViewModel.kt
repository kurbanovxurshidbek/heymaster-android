package com.heymaster.heymaster.role.master.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.master.repository.MasterPortfolioRepository
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.model.masterprofile.Portfolio

import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MasterProfileViewModel(private val repository: MasterPortfolioRepository): ViewModel() {



    private val _portfolios = MutableStateFlow<UiStateList<Portfolio>>(UiStateList.EMPTY)
    val portfolios = _portfolios

    fun getImages() = viewModelScope.launch {
        _portfolios.value = UiStateList.LOADING
        try {
            val response = repository.getImages()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _portfolios.value = UiStateList.ERROR(error.errorMessage)
            } else {
                _portfolios.value = UiStateList.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _portfolios.value = UiStateList.ERROR(e.userMessage())
        }
    }

    private val _masterProfile = MutableStateFlow<UiStateObject<CurrentUser>>(UiStateObject.EMPTY)
    val masterProfile = _masterProfile

    fun getMasterProfile() = viewModelScope.launch {
        _masterProfile.value = UiStateObject.LOADING
        try {
            val response = repository.getMasterProfileInfo()

            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _masterProfile.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _masterProfile.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {

            _masterProfile.value = UiStateObject.ERROR(e.userMessage())
        }
    }




    private val _portfolio = MutableStateFlow<UiStateObject<Portfolio>>(UiStateObject.EMPTY)
    val portfolio = _portfolio

    fun getImage(id: Int) = viewModelScope.launch {
        _portfolio.value = UiStateObject.LOADING
        try {
            val response = repository.getImage(id)
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _portfolio.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _portfolio.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _portfolio.value = UiStateObject.ERROR(e.userMessage())
        }
    }
}