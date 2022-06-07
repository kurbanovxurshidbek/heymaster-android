package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.bottomsheet.RateBottomSheetFragment
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingViewModel
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.utils.extensions.viewBinding


class ClientActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding {FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: ClientBookingViewModel
    private lateinit var rateBottomSheetFragment: RateBottomSheetFragment
    private lateinit var activeBookingAdapter: ClientActiveBookingAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()

    }

    private fun setupRv() {
        val uActiveBookingM = UActiveBookingM(
            1,
            "Electric",
            R.drawable.intro_image_3,
            "Donald Trump",
            "01.01.2021",
            "+998 99 046 6901"
        )
        val list = ArrayList<UActiveBookingM>()
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)


        activeBookingAdapter = ClientActiveBookingAdapter(list, requireContext())
        binding.rvUserActiveBookings.layoutManager = GridLayoutManager(context, 1)
        binding.rvUserActiveBookings.adapter = activeBookingAdapter
        activeBookingAdapter.setItemClickListener(ClientActiveBookingAdapter.ItemClickListener {
            showBottomSheet("rate_bottom_sheet_fragment",it)
        })

    }

    private fun showBottomSheet(tag: String, item: UActiveBookingM) {
        rateBottomSheetFragment = RateBottomSheetFragment.newInstance(item = item)
        rateBottomSheetFragment.show(childFragmentManager, tag)
    }

}