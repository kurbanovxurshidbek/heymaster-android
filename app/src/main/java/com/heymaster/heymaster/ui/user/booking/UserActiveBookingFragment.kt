package com.heymaster.heymaster.ui.user.booking

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.ActiveBookingAdapter
import com.heymaster.heymaster.adapters.booking.bottomsheet.RateBottomSheetFragment
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class UserActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding { FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: UserBookingViewModel
    private lateinit var rateBottomSheetFragment: RateBottomSheetFragment
//    private val activeBookingAdapter by lazy { ActiveBookingAdapter(requireContext()) }

    private lateinit var activeBookingAdapter: ActiveBookingAdapter

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


        activeBookingAdapter = ActiveBookingAdapter(list, requireContext())
        binding.rvUserActiveBookings.setLayoutManager(GridLayoutManager(context, 1))
        binding.rvUserActiveBookings.adapter = activeBookingAdapter
        activeBookingAdapter.setItemClickListener(ActiveBookingAdapter.ItemClickListener {
            showBottomSheet("rate_bottom_sheet_fragment",it)
        })

    }

    private fun showBottomSheet(tag: String, item: UActiveBookingM) {
        rateBottomSheetFragment = RateBottomSheetFragment.newInstance(item = item)
        rateBottomSheetFragment.show(childFragmentManager, tag)
    }


}