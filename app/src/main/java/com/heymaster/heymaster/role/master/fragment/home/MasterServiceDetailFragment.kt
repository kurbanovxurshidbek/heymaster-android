package com.heymaster.heymaster.role.master.fragment.home

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
import com.heymaster.heymaster.databinding.FragmentMasterServiceDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.adapter.MasterHomeProfessionsAdapter
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterServiceDetailFragment : BaseFragment(R.layout.fragment_master_service_detail) {

    private val binding by viewBinding { FragmentMasterServiceDetailBinding.bind(it) }
    private lateinit var viewModel: MasterHomeViewModel

    private val professionAdapter by lazy { MasterHomeProfessionsAdapter() }


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

                    }
                    is UiStateList.SUCCESS -> {

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