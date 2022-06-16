package com.heymaster.heymaster.role.master.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.model.home.HomeResponse
import com.heymaster.heymaster.model.masterdetailpage.MasterDetail
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel (private val repository: DetailsRepository): ViewModel(){

    private val _portfolio = MutableStateFlow<UiStateList<Portfolio>>(UiStateList.EMPTY)

    val portfolio = _portfolio

    fun getImages() = viewModelScope.launch {
        _portfolio.value = UiStateList.LOADING
        try {
            val response = repository.getImages()
            if (response.code() >= 400) {
                val error = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _portfolio.value = UiStateList.ERROR(error.errorMessage)
            }else {
                _portfolio.value = UiStateList.SUCCESS(response.body())
            }
        }catch (e:Exception){
            _portfolio.value = UiStateList.ERROR(e.userMessage())
        }
    }

    private val _masterProfile = MutableStateFlow<UiStateObject<MasterDetail>>(UiStateObject.EMPTY)
    val masterProfile = _masterProfile

    fun getMasterDetail(id: Int) = viewModelScope.launch {
        _masterProfile.value = UiStateObject.LOADING
        try {
            val response = repository.getMasterDetailInfo(id)

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

}