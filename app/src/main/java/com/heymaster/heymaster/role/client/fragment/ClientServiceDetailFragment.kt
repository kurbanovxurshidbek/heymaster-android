package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentServiceDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.adapter.ClientHomeProfessionsAdapter
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientServiceDetailFragment : BaseFragment(R.layout.fragment_service_detail) {

    private val binding by viewBinding { FragmentServiceDetailBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel

    private val professionAdapter by lazy { ClientHomeProfessionsAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        var id = 0
        arguments?.let {
            id = it.getInt("category_id")
        }
        viewModel.getProfessionsFromCategory(id.toString())
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.professionsFromCategory.collect {
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressSearch.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressSearch.customProgress.visibility = View.GONE
                        professionAdapter.submitList(it.data)

                    }
                    is UiStateList.ERROR -> {
                        Log.d("@@@", "observeViewModel: ${it.message}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }


    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createServiceWithAuth(
                ApiService::class.java, token!!)))
        )[ClientHomeViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvHomeProfessionFromCategory.adapter = professionAdapter
    }


}