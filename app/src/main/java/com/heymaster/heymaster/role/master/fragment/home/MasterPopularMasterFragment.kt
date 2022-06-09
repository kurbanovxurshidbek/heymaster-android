package com.heymaster.heymaster.role.master.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterAllPopularBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.adapter.MasterHomePopularMasterAdapter
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPopularMasterFragment : BaseFragment(R.layout.fragment_master_all_popular) {
    private val binding by viewBinding { FragmentMasterAllPopularBinding.bind(it) }
    private lateinit var viewModel: MasterHomeViewModel
    private val popularMastersAdapter by lazy { MasterHomePopularMasterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        viewModel.getServices()
        observeViewModel()


    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.services.collect { it ->
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressMasterHomeAllMasters.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressMasterHomeAllMasters.customProgress.visibility = View.GONE
                        popularMastersAdapter.submitList(it.data)


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
        binding.rvUserAllMaster.adapter = popularMastersAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MasterHomeViewModelFactory(MasterHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[MasterHomeViewModel::class.java]
    }

}