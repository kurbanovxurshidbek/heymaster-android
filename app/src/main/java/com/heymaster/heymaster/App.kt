package com.heymaster.heymaster

import android.app.Application
import com.heymaster.heymaster.manager.LocaleManager

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        localeManager = LocaleManager(this)
        localeManager!!.setLocale(this)
    }

    companion object {
        lateinit var instance:App
        var localeManager:LocaleManager? = null
    }
}