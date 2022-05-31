package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter
import com.heymaster.heymaster.role.client.viewmodel.UserBookingRepository
import com.heymaster.heymaster.role.client.viewmodel.UserBookingViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.UserBookingViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class UserActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding {FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: UserBookingViewModel
    private val clientActiveBookingAdapter by lazy { ClientActiveBookingAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setupViewModel()
        viewModel.getBookings()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.bookings.collect { it ->
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressUserActiveBooking.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressUserActiveBooking.customProgress.visibility = View.GONE
                        clientActiveBookingAdapter.submitList(it.data)

                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRv() {
        binding.rvUserActiveBookings.adapter = clientActiveBookingAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserBookingViewModelFactory(UserBookingRepository(ApiClient.createService(ApiService::class.java)))
        )[UserBookingViewModel::class.java]
    }
}