package com.heymaster.heymaster.global

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import com.heymaster.heymaster.R

open class BaseFragment(private val layoutId: Int): Fragment(layoutId) {

    private var dialog: Dialog? = null

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





}