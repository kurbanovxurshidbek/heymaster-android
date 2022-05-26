package com.heymaster.heymaster.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivitySplashBinding
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.ui.global.BaseActivity
import com.heymaster.heymaster.ui.intro.IntroActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }

        countTimer()


    }

    private fun countTimer() {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                callIntroActivity()
            }

        }.start()
    }






}