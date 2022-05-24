package com.heymaster.heymaster.ui.master.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.home.HomeServicesAdapter
import com.heymaster.heymaster.adapters.profile.PortfolioAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterChildProfileBinding
import com.heymaster.heymaster.databinding.FragmentMasterPortfolioBinding
import com.heymaster.heymaster.databinding.FragmentUserHomeBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.ui.user.home.UserHomeRepository
import com.heymaster.heymaster.ui.user.home.UserHomeViewModel
import com.heymaster.heymaster.ui.user.home.UserHomeViewModelFactory
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPortfolioFragment : BaseFragment(R.layout.fragment_master_portfolio) {
    private val binding by viewBinding { FragmentMasterPortfolioBinding.bind(it) }
    private lateinit var viewModel: MasterProfileViewModel
    private val portfolioAdapter by lazy { PortfolioAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        viewModel.getImages()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.portfolios.collect {
                when(it) {
                    is UiStateList.LOADING -> {

                    }
                    is UiStateList.SUCCESS -> {
                        portfolioAdapter.submitList(it.data)


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
        binding.recyclerViewPortfolio.adapter = portfolioAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MasterProfileViewModelFactory(MasterProfileRepository(ApiClient.createService(ApiService::class.java)))
        )[MasterProfileViewModel::class.java]
    }

}