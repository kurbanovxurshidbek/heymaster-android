package com.heymaster.heymaster.role.client.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.role.master.adapter.DetailsPortfolioAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentDetailsPortfolioBinding
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.role.master.viewmodel.DetailsViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.DetailsViewModelFactory
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.role.master.adapter.MasterPortfolioAdapter
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientDetailsPortfolioFragment : BaseFragment(R.layout.fragment_details_portfolio) {
    private val binding by viewBinding { FragmentDetailsPortfolioBinding.bind(it) }
    private lateinit var viewModel: DetailsViewModel
    private val masterPortfolioAdapter by lazy { MasterPortfolioAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterProfile.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }

                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        val list = ArrayList<Portfolio>()
                        if (!it.data.`object`.attachments.isNullOrEmpty()) {
                            it.data.`object`.attachments.reversed().forEach {
                                list.add(Portfolio.Image(it))
                            }
                        }
                        masterPortfolioAdapter.submitList(list.toList())
                    }

                    is UiStateObject.ERROR -> {

                    }
                }
            }

        }
    }

    private fun setupRv() {
        binding.recyclerViewPortfolio.adapter = masterPortfolioAdapter
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this, DetailsViewModelFactory(DetailsRepository(ApiClient.createServiceWithAuth(ApiService::class.java, token!!
                    )
                )
            )
        )[DetailsViewModel::class.java]
    }
}