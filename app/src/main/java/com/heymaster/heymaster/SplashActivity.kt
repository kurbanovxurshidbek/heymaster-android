package com.heymaster.heymaster

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import com.heymaster.heymaster.databinding.ActivitySplashBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.utils.ConnectivityReceiver
import com.heymaster.heymaster.utils.Constants.KEY_INTRO_SAVED


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupStatusBar()
        }
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun countTimer() {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                //callIntroActivity()
                if (SharedPref(this@SplashActivity).getBoolean(KEY_INTRO_SAVED)) {
                    callLoginActivity()
                } else {
                    callIntroActivity()
                }
            }

        }.start()
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
            countTimer()

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showDialog(isConnected)
    }


}