package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.AttachmentInfo
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.auth.ClientToMasterRequest
import com.heymaster.heymaster.model.auth.ClientToMasterResponse
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ClientProfileViewModel(
    private val repository: ClientProfileRepository
): ViewModel(){

    private val _currentUser = MutableStateFlow<UiStateObject<CurrentUser>>(UiStateObject.EMPTY)
    val currentUser = _currentUser

    private val _uploadAttachment = MutableStateFlow<UiStateObject<Any>>(UiStateObject.EMPTY)
    val uploadAttachment = _uploadAttachment

    private val _uploadProfilePhoto = MutableStateFlow<UiStateObject<Any>>(UiStateObject.EMPTY)
    val uploadProfilePhoto = _uploadProfilePhoto

    private val _attachmentInfo = MutableStateFlow<UiStateList<AttachmentInfo>>(UiStateList.EMPTY)
    val attachmentInfo = _attachmentInfo

    private val _clientToMaster = MutableStateFlow<UiStateObject<ClientToMasterResponse>>(UiStateObject.EMPTY)
    val clientToMaster = _clientToMaster

    private val _clientToMasterIsAlreadyMaster = MutableStateFlow<UiStateObject<ClientToMasterResponse>>(UiStateObject.EMPTY)
    val clientToMasterIsAlreadyMaster = _clientToMasterIsAlreadyMaster




    fun currentUser() = viewModelScope.launch {
        _currentUser.value = UiStateObject.LOADING

        try {
            val response = repository.currentUser()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _currentUser.value = UiStateObject.ERROR(error.errorMessage)

            } else {
                _currentUser.value = UiStateObject.SUCCESS(response.body()!!)
            }

        } catch (e: Exception) {
            _currentUser.value = UiStateObject.ERROR(e.userMessage())
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

    fun attachmentInfo() = viewModelScope.launch {
        _attachmentInfo.value = UiStateList.LOADING

        try {
            val response = repository.attachmentInfo()
            _attachmentInfo.value = UiStateList.SUCCESS(response.body())

        } catch (e: Exception) {
            _attachmentInfo.value = UiStateList.ERROR(e.userMessage())
        }
    }

    fun clientToMaster(clientToMasterRequest: ClientToMasterRequest) = viewModelScope.launch {
        _clientToMaster.value = UiStateObject.LOADING

        try {
            val response = repository.clientToMaster(clientToMasterRequest)
            _clientToMaster.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _clientToMaster.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun clientToMasterIsAlreadyMaster() = viewModelScope.launch {
        _clientToMasterIsAlreadyMaster.value = UiStateObject.LOADING

        try {
            val response = repository.clientToMasterIsAlreadyMaster()
            _clientToMasterIsAlreadyMaster.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _clientToMasterIsAlreadyMaster.value = UiStateObject.ERROR(e.userMessage())
        }
    }
}