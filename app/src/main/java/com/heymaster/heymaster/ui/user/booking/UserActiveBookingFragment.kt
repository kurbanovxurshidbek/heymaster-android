package com.heymaster.heymaster.ui.user.booking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.ActiveBookingAdapter
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class UserActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding {FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: UserBookingViewModel
    private val activeBookingAdapter by lazy { ActiveBookingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_active_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
    }

    private fun setUpRv() {
        binding.rvUserActiveBookings.adapter = activeBookingAdapter
    }

}