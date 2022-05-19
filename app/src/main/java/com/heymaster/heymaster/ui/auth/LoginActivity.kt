package com.heymaster.heymaster.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityLoginBinding
import com.heymaster.heymaster.ui.global.BaseActivity

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}