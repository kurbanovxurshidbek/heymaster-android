package com.heymaster.heymaster.role.master.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.AttachmentInfo
import com.heymaster.heymaster.role.master.repository.MasterProfileRepository
import com.heymaster.heymaster.model.District
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Region
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.model.masterprofile.MasterToClientResponse
import com.heymaster.heymaster.model.auth.Profession
import com.heymaster.heymaster.model.editmasterprofile.EditMasterRequest
import com.heymaster.heymaster.model.editmasterprofile.EditMasterResponse
import com.heymaster.heymaster.model.masterprofile.Portfolio

import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class MasterProfileViewModel(private val repository: MasterProfileRepository): ViewModel() {

    private val _uploadAttachment = MutableStateFlow<UiStateObject<Any>>(UiStateObject.EMPTY)
    val uploadAttachment = _uploadAttachment

    private val _attachmentInfo = MutableStateFlow<UiStateList<AttachmentInfo>>(UiStateList.EMPTY)
    val attachmentInfo = _attachmentInfo

    private val _masterProfile = MutableStateFlow<UiStateObject<CurrentUser>>(UiStateObject.EMPTY)
    val masterProfile = _masterProfile

    private val _masterToClient = MutableStateFlow<UiStateObject<MasterToClientResponse>>(UiStateObject.EMPTY)
    val masterToClient = _masterToClient


    private val _uploadProfilePhoto = MutableStateFlow<UiStateObject<Any>>(UiStateObject.EMPTY)
    val uploadProfilePhoto = _uploadProfilePhoto



    private val _portfolios = MutableStateFlow<UiStateList<Portfolio>>(UiStateList.EMPTY)
    val portfolios = _portfolios

    private val _portfolio = MutableStateFlow<UiStateObject<Portfolio>>(UiStateObject.EMPTY)
    val portfolio = _portfolio


    private val _regions = MutableStateFlow<UiStateList<Region>>(UiStateList.EMPTY)
    val regions = _regions

    private val _districts = MutableStateFlow<UiStateList<District>>(UiStateList.EMPTY)
    val districts = _districts

    private val _professions = MutableStateFlow<UiStateObject<Profession>>(UiStateObject.EMPTY)
    val professions = _professions

    private val _masterEditProfile = MutableStateFlow<UiStateObject<EditMasterResponse>>(UiStateObject.EMPTY)
    val masterEditProfile = _masterEditProfile

    fun editProfileMaster(editMasterRequest: EditMasterRequest) = viewModelScope.launch {
        _masterEditProfile.value = UiStateObject.LOADING
        try {
            val response = repository.editProfileMaster(editMasterRequest)

            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _masterEditProfile.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _masterEditProfile.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _masterEditProfile.value = UiStateObject.ERROR(e.userMessage())
        }
    }

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

    fun uploadAttachment(body: RequestBody) = viewModelScope.launch {
        _uploadAttachment.value = UiStateObject.LOADING

        try {
            val response = repository.uploadAttachment(body)
            if(response.isSuccessful) {
                _uploadAttachment.value = UiStateObject.SUCCESS(response)
            }


        } catch (e: Exception) {
            _uploadAttachment.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun attachmentInfo() = viewModelScope.launch {
        _attachmentInfo.value = UiStateList.LOADING

        try {
            val response = repository.attachmentInfo()
            _attachmentInfo.value = UiStateList.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _attachmentInfo.value = UiStateList.ERROR(e.userMessage())
        }
    }


    fun masterToClient() = viewModelScope.launch {
        _masterToClient.value = UiStateObject.LOADING

        try {
            val response = repository.masterToClient()
            _masterToClient.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _masterToClient.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getRegions() = viewModelScope.launch {
        _regions.value = UiStateList.LOADING

        try {
            val response = repository.getRegions()
            _regions.value = UiStateList.SUCCESS(response.body())

        } catch (e: Exception) {
            _regions.value = UiStateList.ERROR(e.userMessage())
        }
    }

    fun getDistrictsFromRegion(id: Int) = viewModelScope.launch {
        _districts.value = UiStateList.LOADING

        try {
            val response = repository.getDistrictsFromRegion(id)

            _districts.value = UiStateList.SUCCESS(response.body())

        } catch (e: Exception) {
            _districts.value = UiStateList.ERROR(e.userMessage())
        }
    }

    fun getProfessions() = viewModelScope.launch {
        _professions.value = UiStateObject.LOADING

        try {
            val response = repository.getProfessions()
            _professions.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _professions.value = UiStateObject.ERROR(e.userMessage())
        }
    }



    fun uploadProfilePhoto(body: RequestBody) = viewModelScope.launch {
        _uploadProfilePhoto.value = UiStateObject.LOADING

        try {
            val response = repository.uploadProfilePhoto(body)
            if(response.isSuccessful) {
                _uploadProfilePhoto.value = UiStateObject.SUCCESS(response)
            }


        } catch (e: Exception) {
            _uploadProfilePhoto.value = UiStateObject.ERROR(e.userMessage())
        }
    }


}