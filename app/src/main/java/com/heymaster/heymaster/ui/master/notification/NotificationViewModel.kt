package com.heymaster.heymaster.ui.master.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Notification
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    val repository: NotificationRepository
) : ViewModel() {

    private val _notification = MutableStateFlow<UiStateList<Notification>>(UiStateList.EMPTY)

    val notifications = _notification

    fun getNotifications() = viewModelScope.launch {
        _notification.value = UiStateList.LOADING
        try {
            val response = repository.getNotificationSuggestion()
            if (response.code() >=400){
                val error = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _notification.value = UiStateList.ERROR(error.errorMessage)
            }else {
                _notification.value = UiStateList.SUCCESS(response.body()!!)
            }
        }catch (e: Exception){
            _notification.value = UiStateList.ERROR(e.userMessage())
        }
    }

}

