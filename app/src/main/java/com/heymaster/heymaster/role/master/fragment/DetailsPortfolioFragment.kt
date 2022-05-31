package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.master.adapter.DetailsPortfolioAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentDetailsPortfolioBinding
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.role.master.viewmodel.DetailsViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.DetailsViewModelFactory
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class DetailsPortfolioFragment : BaseFragment(R.layout.fragment_details_portfolio) {
    private val binding by viewBinding { FragmentDetailsPortfolioBinding.bind(it) }
    private lateinit var viewModel: DetailsViewModel
    private lateinit var detailsPortfolioAdapter: DetailsPortfolioAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsPortfolioAdapter = DetailsPortfolioAdapter()
        setupRv()
        setupViewModel()
        viewModel.getImages()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.portfolio.collect {
                when(it) {
                    is UiStateList.LOADING -> {

                    }
                    is UiStateList.SUCCESS -> {
                        detailsPortfolioAdapter.submitList(it.data)


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
        binding.recyclerViewPortfolio.adapter = detailsPortfolioAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, DetailsViewModelFactory(
                DetailsRepository(
                    ApiClient.createService(
                        ApiService::class.java))
            )
        )[DetailsViewModel::class.java]
    }
}