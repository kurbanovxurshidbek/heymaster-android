package com.heymaster.heymaster.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthSharedViewModel: ViewModel() {

    val clientSignUp = MutableLiveData<Int>()
    val masterSignUp = MutableLiveData<Int>()

    fun clientSignUp(position: Int) {
        clientSignUp.value = position
    }

    fun masterSignUp(position: Int) {
        masterSignUp.value = position
    }
}