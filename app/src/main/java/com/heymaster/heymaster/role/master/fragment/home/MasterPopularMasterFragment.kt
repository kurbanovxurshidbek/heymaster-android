package com.heymaster.heymaster.role.master.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterAllPopularBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.AllMastersAdapter
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPopularMasterFragment : BaseFragment(R.layout.fragment_master_all_popular) {
    private val binding by viewBinding { FragmentMasterAllPopularBinding.bind(it) }
    private lateinit var viewModel: MasterHomeViewModel
    private val popularMastersAdapter by lazy { AllMastersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        viewModel.getActiveMasters()
        observeViewModel()

        popularMastersAdapter.itemCLickListener = {
            findNavController().navigate(R.id.detailPageFragment, bundleOf("master_id" to it.id))
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.activeMasters.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressMasterHomeAllMasters.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressMasterHomeAllMasters.customProgress.visibility = View.GONE
                        popularMastersAdapter.submitList(it.data.`object`)

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
        binding.rvUserAllMaster.adapter = popularMastersAdapter
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            MasterHomeViewModelFactory(MasterHomeRepository
                (ApiClient.createServiceWithAuth(ApiService::class.java, token!!)))
        )[MasterHomeViewModel::class.java]
    }

}