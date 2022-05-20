package com.heymaster.heymaster.ui.global

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.heymaster.heymaster.R
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.ui.intro.IntroActivity
import com.heymaster.heymaster.ui.master.MasterActivity
import com.heymaster.heymaster.ui.user.UserActivity

open class BaseActivity: AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    fun setupStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun callLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun callIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun callMasterActivity() {
        val intent = Intent(this, MasterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun callUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
        finish()
    }
}