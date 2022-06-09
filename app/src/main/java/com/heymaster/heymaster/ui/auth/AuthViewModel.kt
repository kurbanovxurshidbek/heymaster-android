package com.heymaster.heymaster.ui.auth

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.User
import com.heymaster.heymaster.model.auth.*
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : ViewModel() {


    private val _login = MutableStateFlow<UiStateObject<LoginResponse>>(UiStateObject.EMPTY)
    val login = _login

    private val _confirm = MutableStateFlow<UiStateObject<ConfirmResponse>>(UiStateObject.EMPTY)
    val confirm = _confirm

    private val _clientRegister = MutableStateFlow<UiStateObject<ClientRegisterResponse>>(UiStateObject.EMPTY)
    val clientRegister = _clientRegister






    fun login(phoneNumber: LoginRequest) = viewModelScope.launch {
        _login.value = UiStateObject.LOADING
        try {
            val response = repository.login(phoneNumber)
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _login.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _login.value = UiStateObject.SUCCESS(response.body()!!)
            }


        } catch (e: Exception) {
            _login.value = UiStateObject.ERROR(e.userMessage())

        }
    }

    fun confirm(confirmRequest: ConfirmRequest) = viewModelScope.launch {
        _confirm.value = UiStateObject.LOADING
        try {
            val response = repository.confirm(confirmRequest)
            if (response.code() >= 400) {

                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ConfirmResponse::class.java)
                _confirm.value = UiStateObject.SUCCESS(error)

            } else {
                _confirm.value = UiStateObject.SUCCESS(response.body()!!)
            }


        } catch (e: Exception) {
            _login.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun clientRegister(clientRegisterRequest: ClientRegisterRequest) = viewModelScope.launch {
        _clientRegister.value = UiStateObject.LOADING

        try {

            val response = repository.clientRegister(clientRegisterRequest)
            if (response.code() >= 400) {

                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _confirm.value = UiStateObject.ERROR(error.errorMessage)

            } else {
                _clientRegister.value = UiStateObject.SUCCESS(response.body()!!)

            }

        } catch (e: Exception) {
            _clientRegister.value = UiStateObject.ERROR(e.userMessage())
        }
    }



    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedGame: LiveData<String>
        get() = _formattedTime



    fun startTimer() {
        timer = object : CountDownTimer(
            10 * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS

        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                _formattedTime.value = "00:00"
            }
        }
        timer?.start()
    }


    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)


    }


    companion object {

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


}