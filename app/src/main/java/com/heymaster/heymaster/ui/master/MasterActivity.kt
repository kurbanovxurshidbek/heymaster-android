package com.heymaster.heymaster.ui.master

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heymaster.heymaster.R
import com.heymaster.heymaster.ui.global.BaseActivity

class MasterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }

    }
}