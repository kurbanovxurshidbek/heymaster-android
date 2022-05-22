package com.heymaster.heymaster.ui.master

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityMasterBinding
import com.heymaster.heymaster.ui.global.BaseActivity

class MasterActivity : BaseActivity() {

    private val binding by lazy { ActivityMasterBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }

        binding.userBottomNavigation.setupWithNavController(findNavController(R.id.master_container))

    }
}