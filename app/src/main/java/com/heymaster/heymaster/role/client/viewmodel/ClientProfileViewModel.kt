package com.heymaster.heymaster.role.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ClientProfileViewModel(
    private val repository: ClientProfileRepository
): ViewModel(){

    private val _currentUser = MutableStateFlow<UiStateObject<CurrentUser>>(UiStateObject.EMPTY)
    val currentUser = _currentUser

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
}