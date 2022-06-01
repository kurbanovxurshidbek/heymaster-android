package com.heymaster.heymaster.ui.master.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterPortfolioBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.role.master.adapter.MasterPortfolioAdapter
import com.heymaster.heymaster.role.master.repository.MasterPortfolioRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterPortfolioViewModelFactory
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPortfolioFragment : BaseFragment(R.layout.fragment_master_portfolio) {
    private val binding by viewBinding { FragmentMasterPortfolioBinding.bind(it) }
    private lateinit var viewModel: MasterProfileViewModel
    private val portfolioAdapter by lazy { MasterPortfolioAdapter() }

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
            viewModel.portfolios.collect() {
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressBarPortfolio.visibility = View.GONE
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressBarPortfolio.visibility = View.GONE
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
        val list = ArrayList<Portfolio>()
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        list.add(Portfolio.Image("https://images.unsplash.com/photo-1638913660695-b490171d17c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"))
        portfolioAdapter.submitList(list)
        binding.recyclerViewPortfolio.adapter = portfolioAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MasterPortfolioViewModelFactory(
                MasterPortfolioRepository(
                    ApiClient.createService(
                        ApiService::class.java
                    )
                )
            )
        )[MasterProfileViewModel::class.java]
    }

}