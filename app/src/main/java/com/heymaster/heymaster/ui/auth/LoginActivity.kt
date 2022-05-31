package com.heymaster.heymaster.ui.auth

import android.os.Build
import android.os.Bundle
import com.heymaster.heymaster.databinding.ActivityLoginBinding
import com.heymaster.heymaster.global.BaseActivity

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }


    }
}