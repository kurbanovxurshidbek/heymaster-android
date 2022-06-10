package com.heymaster.heymaster.ui.auth

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.databinding.ActivityLoginBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.utils.Constants

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }
        loadFCMToken()


    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("@@@Error firebase", "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Log.d("@@@firebase token", token.toString())
            SharedPref(this).saveString(Constants.KEY_DEVICE_TOKEN, token.toString())
        })
    }
}