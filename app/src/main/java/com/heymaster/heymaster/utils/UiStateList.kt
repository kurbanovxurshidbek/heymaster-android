package com.heymaster.heymaster.utils

import com.heymaster.heymaster.model.Service

sealed class UiStateList<out T> {
    data class SUCCESS<out T>(val data: List<T>?):UiStateList<T>()
    data class ERROR(val message:String):UiStateList<Nothing>()
    object LOADING : UiStateList<Nothing>()
    object EMPTY : UiStateList<Nothing>()
}
