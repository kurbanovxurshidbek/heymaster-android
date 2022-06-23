package com.heymaster.heymaster

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import com.heymaster.heymaster.manager.LocaleManager

class App: Application() {

    companion object {
        lateinit var instance:App
        var localeManager:LocaleManager? = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager!!.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        LocaleManager(this).setLocale(this)
        instance = this
        localeManager = LocaleManager(this)
    }


}