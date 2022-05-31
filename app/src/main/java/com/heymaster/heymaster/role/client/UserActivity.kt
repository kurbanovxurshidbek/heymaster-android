package com.heymaster.heymaster.role.client

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityUserBinding
import com.heymaster.heymaster.ui.global.BaseActivity

class UserActivity : BaseActivity() {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }
        binding.userBottomNavigation.setupWithNavController(findNavController(R.id.user_container))


        findNavController(R.id.user_container).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.userNotificationFragment -> hideBottomNav()
                R.id.userSearchFragment -> hideBottomNav()
                R.id.userAllServicesFragment -> hideBottomNav()
                R.id.userAllPopularServiceFragment -> hideBottomNav()
                R.id.userAllPopularMastersFragment -> hideBottomNav()
                R.id.detailPageFragment2 -> hideBottomNav()
                R.id.serviceDetailFragment -> hideBottomNav()
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
