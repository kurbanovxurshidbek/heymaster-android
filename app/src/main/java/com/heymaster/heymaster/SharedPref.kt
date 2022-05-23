package com.heymaster.heymaster

import android.content.Context

class SharedPref(context: Context) {

    private val pref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

    val token: String? = null

    fun isSaved(isSaved: Boolean) {
        val editor = pref.edit()
        editor.putBoolean("isSaved", isSaved)
        editor.apply()
    }

    fun getSaved(): Boolean{
        return pref.getBoolean("isSaved", false)
    }
}