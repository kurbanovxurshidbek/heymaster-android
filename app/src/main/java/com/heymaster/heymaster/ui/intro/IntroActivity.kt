package com.heymaster.heymaster.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}