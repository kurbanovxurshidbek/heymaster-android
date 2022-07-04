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
import android.view.View
import com.heymaster.heymaster.databinding.ActivitySplashBinding
import com.heymaster.heymaster.global.BaseActivity
import com.heymaster.heymaster.manager.LocaleManager
import com.heymaster.heymaster.utils.ConnectivityReceiver
import com.heymaster.heymaster.utils.Constants.CLIENT
import com.heymaster.heymaster.utils.Constants.KEY_INTRO_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        LocaleManager(this).setLocale(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupSplashStatusBar()
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun countTimer() {
        object : CountDownTimer(1000, 2000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                if (SharedPref(this@SplashActivity).getBoolean(KEY_INTRO_SAVED)) {
                    if (SharedPref(this@SplashActivity).getBoolean(KEY_LOGIN_SAVED)) {
                        if (SharedPref(this@SplashActivity).getString(KEY_USER_ROLE) == CLIENT) {
                            callUserActivity()
                        } else {
                            callMasterActivity()
                        }
                    } else {
                        callLoginActivity()
                    }
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