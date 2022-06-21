package com.heymaster.heymaster.role.client

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ActivityUserBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.utils.ConnectivityReceiver

class ClientActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    private var dialog: Dialog? = null

    @SuppressLint("ResourceType")
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
                R.id.professionDetailFragment -> hideBottomNav()
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
}
