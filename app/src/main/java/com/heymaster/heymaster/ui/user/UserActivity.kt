package com.heymaster.heymaster.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}