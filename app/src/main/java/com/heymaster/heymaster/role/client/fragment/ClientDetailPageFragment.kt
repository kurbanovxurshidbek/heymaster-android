package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.role.master.adapter.DetailBottomViewPagerAdapter
import com.heymaster.heymaster.databinding.FragmentDetailPageClientBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.fragment.detail.DetailsHistoryFragment
import com.heymaster.heymaster.role.master.fragment.detail.DetailsPortfolioFragment
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.role.master.viewmodel.DetailsViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.DetailsViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientDetailPageFragment : BaseFragment(R.layout.fragment_detail_page_client) {

    private val binding by viewBinding { FragmentDetailPageClientBinding.bind(it) }
    private val adapter by lazy { DetailBottomViewPagerAdapter(childFragmentManager, lifecycle) }
    private lateinit var viewModel: DetailsViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViewPager()
        var id = 0
        arguments?.let {
            id= it.getInt("master_id")
        }
        viewModel.getMasterDetail(id)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterProfile.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        Log.d("@@@loading", "observeViewModel: loading")

                    }
                    is UiStateObject.SUCCESS -> {
                        Log.d("@@@success", "observeViewModel: loading")


                    }
                    is UiStateObject.ERROR -> {
                        Log.d("@@@error", "observeViewModel: error")
                    }
                    else -> Unit
                }
            }
        }
    }
    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this, DetailsViewModelFactory(
                DetailsRepository(
                    ApiClient.createServiceWithAuth(
                        ApiService::class.java, token!!
                    )
                )
            )
        )[DetailsViewModel::class.java]
    }
    private fun setupViewPager() {
        adapter.addFragment(DetailsPortfolioFragment())
        adapter.addFragment(DetailsHistoryFragment())

        binding.detailBottomViewPager.adapter = adapter

        binding.detailBottomTabLayout.addTab(binding.detailBottomTabLayout.newTab().setText("Portfolio"))
        binding.detailBottomTabLayout.addTab(binding.detailBottomTabLayout.newTab().setText("Profile"))

        binding.detailBottomTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.detailBottomViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.detailBottomViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.detailBottomTabLayout.selectTab(binding.detailBottomTabLayout.getTabAt(position))

            }
        })
    }
}