package com.heymaster.heymaster.role.client.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserAllPopularServiceBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.AllProfessionsAdapter
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.toast
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientAllProfessionsFragment : BaseFragment(R.layout.fragment_user_all_popular_service) {

    private val binding by viewBinding { FragmentUserAllPopularServiceBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel
    private val professionsAdapter by lazy { AllProfessionsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRv()
        viewModel.getProfessions()
        obServeViewModel()

        professionsAdapter.itemClickListener = {
            findNavController().navigate(R.id.action_userAllPopularServiceFragment_to_professionDetailFragment, bundleOf("profession_id" to it.id))
        }



    }

    private fun obServeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.professions.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        professionsAdapter.submitList(it.data.`object`)
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
        binding.rvUserAllPopularService.adapter = professionsAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[ClientHomeViewModel::class.java]
    }


}