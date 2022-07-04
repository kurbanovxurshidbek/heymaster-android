package com.heymaster.heymaster.role.master.fragment.booking

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterHistoryBookingBinding
import com.heymaster.heymaster.role.master.viewmodel.MasterBookingViewModel
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.adapter.MasterHistoryBookingAdapter
import com.heymaster.heymaster.role.master.repository.MasterBookingRepository
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterBookingViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.toast
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterHistoryBookingFragment: BaseFragment(R.layout.fragment_master_history_booking) {

    private val binding by viewBinding { FragmentMasterHistoryBookingBinding.bind(it) }
    private lateinit var viewModel: MasterBookingViewModel
    private val historyAdapter by lazy { MasterHistoryBookingAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupViewModel()
        viewModel.getMasterHistoryBooking()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterHistoryBooking.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }

                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        if (it.data.`object`.isNotEmpty()) {
                            binding.lottieEmpty.visibility = View.GONE
                            historyAdapter.submitList(it.data.`object`.reversed())
                        } else {
                            binding.lottieEmpty.visibility = View.VISIBLE
                        }
                    }

                    is UiStateObject.ERROR -> {
                        dismissLoading()
                        binding.lottieEmpty.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()


                    }
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvMasterHistoryBooking.adapter = historyAdapter
    }


    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            MasterBookingViewModelFactory(MasterBookingRepository(ApiClient.createServiceWithAuth(
                ApiService::class.java, token!!)))
        )[MasterBookingViewModel::class.java]
    }



}