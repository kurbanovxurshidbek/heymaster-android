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


    fun saveString(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun saveInt(key: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    fun saveFloat(key: String, value: Float) {
        val editor = pref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String): Float {
        return pref.getFloat(key, 0F)
    }


}