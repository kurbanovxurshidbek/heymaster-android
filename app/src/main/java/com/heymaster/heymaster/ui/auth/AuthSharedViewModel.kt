package com.heymaster.heymaster.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthSharedViewModel: ViewModel() {

    val click = MutableLiveData<Int>()

    fun clickListener(position: Int) {
        click.value = position
    }
}