package com.heymaster.heymaster.role.client

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityUserBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.role.client.fragment.profile.ClientNotificationFragment
import com.heymaster.heymaster.utils.ConnectivityReceiver
import com.heymaster.heymaster.utils.Constants.CLIENT
import kotlin.math.log

class ClientActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    private var dialog: Dialog? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController = Navigation.findNavController(this,R.id.user_container)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }

        navigate(intent)

        binding.userBottomNavigation.setupWithNavController(findNavController(R.id.user_container))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.userNotificationFragment -> hideBottomNav()
                R.id.userSearchFragment -> hideBottomNav()
                R.id.userAllServicesFragment -> hideBottomNav()
                R.id.userAllPopularServiceFragment -> hideBottomNav()
                R.id.userAllPopularMastersFragment -> hideBottomNav()
                R.id.detailPageFragment2 -> hideBottomNav()
                R.id.serviceDetailFragment -> hideBottomNav()
                R.id.professionDetailFragment -> hideBottomNav()
                R.id.clientToMasterFragment -> hideBottomNav()
                R.id.clientEditProfileFragment -> hideBottomNav()
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navController.navigate(R.id.userNotificationFragment)
    }

    private fun navigate(intent: Intent) {
        val text = intent.getStringExtra("key")
            if (text == CLIENT) {
                Log.d("@@@", "navigate: $text")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.user_container, ClientNotificationFragment())
                    .commit()
            }
        }
}
