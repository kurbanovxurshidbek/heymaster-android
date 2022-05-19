package com.heymaster.heymaster

import android.content.Context

class SharedPref(context: Context) {

    val token: String? = null

    private val pref = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

    fun isSaved(isSaved: Boolean) {
        val editor = pref.edit()
        editor.putBoolean("isSaved", isSaved)
        editor.apply()
    }

    fun getSaved(): Boolean{
        return pref.getBoolean("isSaved", false)
    }
}