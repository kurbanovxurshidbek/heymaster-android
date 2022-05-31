package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientHistoryBookingAdapter
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientBookingViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserHistoryBookingBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientHistoryBookingFragment : BaseFragment(R.layout.fragment_user_history_booking) {

    private val binding by viewBinding { FragmentUserHistoryBookingBinding.bind(it) }
    private lateinit var viewModel: ClientBookingViewModel
    private val adapter by lazy { ClientHistoryBookingAdapter() }

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
                        binding.progressUserHistoryBooking.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressUserHistoryBooking.customProgress.visibility = View.GONE
                        adapter.submitList(it.data)

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
        binding.rvUserHistoryBookings.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ClientBookingViewModelFactory(ClientBookingRepository(ApiClient.createService(ApiService::class.java)))
        )[ClientBookingViewModel::class.java]
    }


}