package com.heymaster.heymaster.role.client.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserAllPopularMastersBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.AllMastersAdapter
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientAllMastersFragment : BaseFragment(R.layout.fragment_user_all_popular_masters) {

    private val binding by viewBinding { FragmentUserAllPopularMastersBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel
    private val popularMastersAdapter by lazy { AllMastersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRv()
        viewModel.getActiveMasters()
        observeViewModel()


    }



    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.activeMasters.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        popularMastersAdapter.submitList(it.data.`object`)



                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvUserAllMaster.adapter = popularMastersAdapter

        popularMastersAdapter.itemCLickListener = {
            launchMasterDetailFragment(it.id)
        }
    }

    private fun launchMasterDetailFragment(id: Int) {
        findNavController().navigate(R.id.action_userAllPopularMastersFragment_to_detailPageFragment2, bundleOf("master_id" to id))

    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createServiceWithAuth(ApiService::class.java, token!!)))
        )[ClientHomeViewModel::class.java]
    }


}