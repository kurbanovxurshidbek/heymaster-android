package com.heymaster.heymaster.role.client.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentProfessionDetailBinding
import com.heymaster.heymaster.databinding.FragmentServiceDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.PopularMastersAdapter
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientProfessionDetailFragment : BaseFragment(R.layout.fragment_profession_detail) {

    private val binding by viewBinding { FragmentProfessionDetailBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel

    private val mastersAdapter by lazy { PopularMastersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        var id = 0
        arguments?.let {
            id = it.getInt("profession_id")
        }
        viewModel.getMastersFromProfessionId(id)

        observeViewModel()


    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.mastersFromProfessionId.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        mastersAdapter.submitList(it.data.`object`)

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                        Toast.makeText(requireContext(), it.message,Toast.LENGTH_SHORT).show()

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

    private fun setupRecyclerView() {
        binding.rvMasters.adapter = mastersAdapter
    }

}