package com.heymaster.heymaster.role.master

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityMasterBinding
import com.heymaster.heymaster.global.BaseActivity

class MasterActivity : BaseActivity() {

    private val binding by lazy { ActivityMasterBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }

        binding.userBottomNavigation.setupWithNavController(findNavController(R.id.master_container))

        findNavController(R.id.master_container).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.notificationFragment -> hideBottomNav()
                R.id.masterSearchFragment -> hideBottomNav()
                R.id.detailPageFragment -> hideBottomNav()
                R.id.masterAllServicesFragment -> hideBottomNav()
                R.id.masterPopularMasterFragment -> hideBottomNav()
                R.id.masterPopularServicesFragment -> hideBottomNav()
                R.id.masterEditProfileFragment -> hideBottomNav()
                R.id.masterProfileHelpFragment -> hideBottomNav()
                R.id.masterProfileAboutFragment -> hideBottomNav()

                else -> showBottomNav()
            }
        }
    }

    private fun hideBottomNav() {
        binding.userBottomNavigation.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.userBottomNavigation.visibility = View.VISIBLE
    }
}