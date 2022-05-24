package com.heymaster.heymaster.ui.user.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.home.HomeServicesAdapter
import com.heymaster.heymaster.adapters.viewpagers.AdsPagerAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserHomeBinding
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class UserHomeFragment : BaseFragment(R.layout.fragment_user_home) {

    private val binding by viewBinding { FragmentUserHomeBinding.bind(it) }

    private lateinit var viewModel: UserHomeViewModel
    private val serviceAdapter by lazy { HomeServicesAdapter() }
    private val adsAdapter by lazy { AdsPagerAdapter() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        viewModel.getProducts()
        viewModel.getAds()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.services.collect { it ->
                when(it) {
                    is UiStateList.LOADING -> {
                    }
                    is UiStateList.SUCCESS -> {
                        serviceAdapter.submitList(it.data)
                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }

                viewModel.ads.collect {
                    when(it) {
                        is UiStateList.LOADING -> {
                        }
                        is UiStateList.SUCCESS -> {
                            Log.d("@@@Ads", it.data.toString())
                            adsAdapter.submitAds(it.data)
                        }
                        is UiStateList.ERROR -> {
                            Log.d("@@@error", it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvUserHomeService.adapter = serviceAdapter
        //binding.vpUserHomeAds.adapter = adsAdapter

    }



    private fun fakeAds(): List<Ads> {
        val list = ArrayList<Ads>()
        list.add(Ads(1, "Hello", "", ""))
        list.add(Ads(1, "Hello", "", ""))
        list.add(Ads(1, "Hello", "", ""))
        return list

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserHomeViewModelFactory(UserHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[UserHomeViewModel::class.java]
    }


}