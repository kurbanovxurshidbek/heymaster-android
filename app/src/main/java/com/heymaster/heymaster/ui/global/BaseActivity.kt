package com.heymaster.heymaster.ui.global

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.ui.intro.IntroActivity
import com.heymaster.heymaster.ui.master.MasterActivity
import com.heymaster.heymaster.ui.user.UserActivity

open class BaseActivity: AppCompatActivity() {

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