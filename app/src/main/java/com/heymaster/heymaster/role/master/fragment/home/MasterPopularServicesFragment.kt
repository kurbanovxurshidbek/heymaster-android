package com.heymaster.heymaster.role.master.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterAllPopularServicesBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.AllProfessionsAdapter
import com.heymaster.heymaster.role.master.adapter.MasterHomeAllServicesAdapter
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterPopularServicesFragment : BaseFragment(R.layout.fragment_master_all_popular_services) {


    private val binding by viewBinding { FragmentMasterAllPopularServicesBinding.bind(it) }
    private lateinit var viewModel: MasterHomeViewModel
    private val serviceAdapter by lazy { AllProfessionsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        viewModel.getProfessions()
        obServeViewModel()


        serviceAdapter.itemClickListener = {
            launchCategoryDetailFragment(it.id)
        }

    }
    private fun launchCategoryDetailFragment(id: Int) {
        findNavController().navigate(R.id.masterProfessionsDetailFragment, bundleOf("profession_id" to id))
    }

    private fun obServeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.professions.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressMasterHomeAllService.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressMasterHomeAllService.customProgress.visibility = View.GONE
                        serviceAdapter.submitList(it.data.`object`)


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
        binding.rvMasterAllPopularService.adapter = serviceAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MasterHomeViewModelFactory(MasterHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[MasterHomeViewModel::class.java]
    }



}