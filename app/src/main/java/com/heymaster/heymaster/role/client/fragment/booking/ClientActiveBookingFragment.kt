package com.heymaster.heymaster.role.client.fragment.booking

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.role.client.bottomsheet.RateBottomSheetFragment
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter
import com.heymaster.heymaster.role.client.viewmodel.ClientBookingViewModel
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.repository.ClientBookingRepository
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientBookingViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding {FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: ClientBookingViewModel

    private val activeBookingAdapter by lazy { ClientActiveBookingAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRv()
        setupViewModel()
        viewModel.getClientActiveBooking()

        observeViewModel()




        activeBookingAdapter.clickFinished = {

            val bottomSheet = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.custom_bottom_sheet, null)
            bottomSheet.setContentView(view)

            bottomSheet.show()
        }


    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.activeBooking.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                        Log.d("@@@loading", "observeViewModel: loading")

                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        Log.d("@@@success", "observeViewModel: ${it.data}")
                        if (it.data.`object`.isNotEmpty()) {
                            binding.lottieEmpty.visibility = View.GONE
                            activeBookingAdapter.submitList(it.data.`object`.reversed())
                        } else {
                            binding.lottieEmpty.visibility = View.VISIBLE
                        }


                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()

                    }
                    else -> Unit
                }
            }
        }

    }

    private fun setupRv() {
        binding.rvUserActiveBookings.adapter = activeBookingAdapter
    }



    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientBookingViewModelFactory(ClientBookingRepository(ApiClient.createServiceWithAuth(
                ApiService::class.java, token!!)))
        )[ClientBookingViewModel::class.java]
    }

}