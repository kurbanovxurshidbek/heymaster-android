package com.heymaster.heymaster.role.master.fragment.booking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter
import com.heymaster.heymaster.databinding.FragmentMasterActiveBookingBinding
import com.heymaster.heymaster.role.master.viewmodel.MasterBookingViewModel
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.booking.BookingAcceptRequest
import com.heymaster.heymaster.role.master.repository.MasterBookingRepository
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterBookingViewModelFactory
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.toast
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterActiveBookingFragment : BaseFragment(R.layout.fragment_master_active_booking) {

    private val binding by viewBinding { FragmentMasterActiveBookingBinding.bind(it) }
    private lateinit var viewModel: MasterBookingViewModel
    private val masterActiveBookingAdapter by lazy { MasterActiveBookingAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setupViewModel()

        viewModel.getMasterActiveBooking()
        observeViewModel()

        itemClickListener()
    }

    private fun itemClickListener() {
        masterActiveBookingAdapter.acceptListener = {
            viewModel.bookingAcceptOrCancel(BookingAcceptRequest(it.id, true))
            Toast.makeText(requireContext(), "Accept", Toast.LENGTH_SHORT).show()
        }

        masterActiveBookingAdapter.cancelListener = {
            viewModel.bookingAcceptOrCancel(BookingAcceptRequest(it.id, false))
            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterActiveBooking.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        masterActiveBookingAdapter.submitList(it.data.`object`)

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterActiveBooking.collect {
                when(it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        masterActiveBookingAdapter.submitList(it.data.`object`)

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.bookingAcceptOrCancel.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        viewModel.getMasterActiveBooking()

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                }
            }
        }


    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            MasterBookingViewModelFactory(MasterBookingRepository(ApiClient.createServiceWithAuth(
                ApiService::class.java, token!!)))
        )[MasterBookingViewModel::class.java]
    }

    private fun setUpRv() {
        binding.rvMasterActiveBooking.adapter = masterActiveBookingAdapter
    }

}