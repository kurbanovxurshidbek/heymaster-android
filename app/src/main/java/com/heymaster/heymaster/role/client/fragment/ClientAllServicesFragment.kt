package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientHomeAllServicesAdapter
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserAllServicesBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientAllServicesFragment : BaseFragment(R.layout.fragment_user_all_services) {

    private val binding by viewBinding { FragmentUserAllServicesBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel
    private val serviceAdapter by lazy { ClientHomeAllServicesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        //viewModel.getHome()
        obServeViewModel()

    }

    private fun obServeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.home.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressUserHomeAllService.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressUserHomeAllService.customProgress.visibility = View.GONE



                    }
                    is UiStateObject.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvUserAllService.adapter = serviceAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[ClientHomeViewModel::class.java]
    }



}