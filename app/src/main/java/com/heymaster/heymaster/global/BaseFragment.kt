package com.heymaster.heymaster.global

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.heymaster.heymaster.R
import java.util.*

open class BaseFragment(private val layoutId: Int): Fragment(layoutId) {

    private var dialog: Dialog? = null
    private var defaultStatusBarColor: Int? = null

    fun showLoading() {
        dialog = Dialog(requireContext())
        dialog!!.setContentView(R.layout.dialog_progress)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }

    fun dismissLoading() {
        dialog!!.dismiss()
    }


    fun transparentStatusBar(activity: Activity, isTransparent: Boolean, fullscreen: Boolean) {

        if (isTransparent) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            (Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar?.hide()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                defaultStatusBarColor = activity.window.statusBarColor
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                // FOR TRANSPARENT NAVIGATION BAR
                //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.window.statusBarColor = Color.TRANSPARENT
                Log.d("TAG",
                    "Setting Color Transparent " + Color.TRANSPARENT.toString() + " Default Color " + defaultStatusBarColor)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.d("TAG", "Setting Color Trans " + Color.TRANSPARENT)
                    activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                }
            }
        } else {
            if (fullscreen) {
                val decorView = activity.window.decorView
                val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
                decorView.systemUiVisibility = uiOptions
            } else {
                (Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar!!.show()
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                    activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    activity.window.statusBarColor = defaultStatusBarColor!!
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    }
                }
            }
        }
    }





}