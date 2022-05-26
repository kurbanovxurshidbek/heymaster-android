package com.heymaster.heymaster.ui.master.booking

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.MasterActiveBookingAdapter
import com.heymaster.heymaster.databinding.FragmentMasterActiveBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class MasterActiveBookingFragment : BaseFragment(R.layout.fragment_master_active_booking) {

    private val binding by viewBinding { FragmentMasterActiveBookingBinding.bind(it) }
    private lateinit var viewModel: MasterBookingViewModel
    private lateinit var masterActiveBookingAdapter: MasterActiveBookingAdapter
    //private val masterActiveBookingAdapter by lazy { MasterActiveBookingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
    }

    private fun setUpRv() {
        val uActiveBookingM = UActiveBookingM(1,"Electric",R.drawable.intro_image_3,"Donald Trump","01.01.2021","+998 99 046 6901")
        val list = ArrayList<UActiveBookingM>()
        list.add(uActiveBookingM)

        masterActiveBookingAdapter = MasterActiveBookingAdapter(list)
        binding.rvMasterActiveBooking.adapter = masterActiveBookingAdapter
    }

}