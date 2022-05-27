package com.heymaster.heymaster.ui.master.booking

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.HistoryBookingAdapter
import com.heymaster.heymaster.adapters.booking.MasterHistoryBookingAdapter
import com.heymaster.heymaster.databinding.FragmentMasterHistoryBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class MasterHistoryBookingFragment: BaseFragment(R.layout.fragment_master_history_booking) {

    private val binding by viewBinding { FragmentMasterHistoryBookingBinding.bind(it) }
    private lateinit var viewModel: MasterBookingViewModel
    private lateinit var masterHistoryBookingAdapter: MasterHistoryBookingAdapter
    //private val masterHistoryBookingAdapter by lazy { MasterHistoryBookingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
    }

    private fun setUpRv() {
        val uActiveBookingM = UActiveBookingM(1,"Electric",R.drawable.intro_image_3,"Donald Trump","01.01.2021","+998 99 046 6901")
        val list = ArrayList<UActiveBookingM>()
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        masterHistoryBookingAdapter = MasterHistoryBookingAdapter(list)
        binding.rvMasterHistoryBooking.setLayoutManager(GridLayoutManager(context,1))
        binding.rvMasterHistoryBooking.adapter = masterHistoryBookingAdapter
    }

}