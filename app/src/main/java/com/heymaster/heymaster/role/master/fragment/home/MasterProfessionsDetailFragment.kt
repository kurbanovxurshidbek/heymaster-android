package com.heymaster.heymaster.role.master.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterProfessionsDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.global.adapter.home.PopularMastersAdapter
import com.heymaster.heymaster.global.adapter.home.ProfessionsFromCategoryAdapter
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterProfessionsDetailFragment : BaseFragment(R.layout.fragment_master_professions_detail) {
    private val binding by viewBinding { FragmentMasterProfessionsDetailBinding.bind(it) }
    private lateinit var viewModel:MasterHomeViewModel
    private val professionAdapter by lazy { PopularMastersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRv()
        adapterItemClick()
        var id = 0
        arguments?.let {
            id = it.getInt("profession_id")
        }
        viewModel.getMastersFromProfessionId(id)
        observeViewModel()

    }
    private fun adapterItemClick(){
        professionAdapter.itemCLickListener = {
            findNavController().navigate(R.id.detailPageFragment, bundleOf("master_id" to it.id))
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.mastersFromProfessionId.collect {
                when (it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        professionAdapter.submitList(it.data.`object`)

                    }
                    is UiStateObject.ERROR -> {
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
            MasterHomeViewModelFactory(
                MasterHomeRepository(
                    ApiClient.createServiceWithAuth(
                        ApiService::class.java, token!!))
            )
        )[MasterHomeViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvHomeProfessionFromCategory.adapter = professionAdapter
    }

}