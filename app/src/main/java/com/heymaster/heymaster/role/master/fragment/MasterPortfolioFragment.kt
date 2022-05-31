package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.master.adapter.MasterPortfolioAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterPortfolioBinding
import com.heymaster.heymaster.role.master.repository.MasterPortfolioRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterPortfolioViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterPortfolioViewModelFactory
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPortfolioFragment : BaseFragment(R.layout.fragment_master_portfolio) {
    private val binding by viewBinding { FragmentMasterPortfolioBinding.bind(it) }
    private lateinit var viewModel: MasterPortfolioViewModel
    private val masterPortfolioAdapter by lazy { MasterPortfolioAdapter() }

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
                        masterPortfolioAdapter.submitList(it.data)


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
        binding.recyclerViewPortfolio.adapter = masterPortfolioAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MasterPortfolioViewModelFactory(MasterPortfolioRepository(ApiClient.createService(ApiService::class.java)))
        )[MasterPortfolioViewModel::class.java]
    }

}