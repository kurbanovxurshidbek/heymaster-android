package com.heymaster.heymaster.ui.master.booking

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterActiveBookingBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class MasterActiveBookingFragment : BaseFragment(R.layout.fragment_master_active_booking) {

    private val binding by viewBinding { FragmentMasterActiveBookingBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}