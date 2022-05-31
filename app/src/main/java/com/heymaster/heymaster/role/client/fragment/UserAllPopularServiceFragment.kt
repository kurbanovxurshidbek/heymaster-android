package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientHomeAllServicesAdapter
import com.heymaster.heymaster.role.client.repository.UserHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.UserHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.UserHomeViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserAllPopularServiceBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class UserAllPopularServiceFragment : BaseFragment(R.layout.fragment_user_all_popular_service) {

    private val binding by viewBinding { FragmentUserAllPopularServiceBinding.bind(it) }
    private lateinit var viewModel: UserHomeViewModel
    private val serviceAdapter by lazy { ClientHomeAllServicesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        viewModel.getServices()
        obServeViewModel()

    }

    private fun obServeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.services.collect { it ->
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressUserHomeAllService.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressUserHomeAllService.customProgress.visibility = View.GONE
                        serviceAdapter.submitList(it.data)


                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvUserAllPopularService.adapter = serviceAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserHomeViewModelFactory(UserHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[UserHomeViewModel::class.java]
    }


}