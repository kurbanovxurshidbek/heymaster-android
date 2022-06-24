package com.heymaster.heymaster.role.master

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityMasterBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.manager.LocaleManager
import com.heymaster.heymaster.utils.ConnectivityReceiver

class MasterActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val binding by lazy { ActivityMasterBinding.inflate(layoutInflater) }
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseAnalytics.getInstance(this)
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
                R.id.masterServiceDetailFragment -> hideBottomNav()
                R.id.portfolioImageDetailFragment -> hideBottomNav()
                R.id.masterProfessionsDetailFragment -> hideBottomNav()

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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showDialog(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun showDialog(isConnected: Boolean) {
        if (!isConnected) {
            dialog = Dialog(this)
            dialog?.setContentView(R.layout.dialog_check_connection)
            dialog?.setCancelable(false)
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.show()
        } else {
            dialog?.dismiss()

        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigate(intent!!)

    }

    private fun navigate(intent: Intent) {
        if (intent != null) {
            val text = intent.getStringExtra("type")
            when(text) {
                "parcel" -> {
                    //navigate to fragment
                    Toast.makeText(this, "Navigate to parcel Fragment", Toast.LENGTH_SHORT).show()
                }
                "simple" -> {
                    Toast.makeText(this, "Navigate to Simple Fragment", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}