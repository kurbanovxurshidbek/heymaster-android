package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularMastersAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularServicesAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeServicesAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeAdsPagerAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterHomeBinding
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterHomeViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterHomeViewModelFactory
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive

class MasterHomeFragment : BaseFragment(R.layout.fragment_master_home) {

    private val binding by viewBinding { FragmentMasterHomeBinding.bind(it) }

    private var job:Job? = null

    private lateinit var viewModel: MasterHomeViewModel
    private val serviceAdapter by lazy { ClientHomeServicesAdapter() }
    private val adsAdapter by lazy { ClientHomeAdsPagerAdapter() }
    private val popularServicesAdapter by lazy { ClientHomePopularServicesAdapter() }
    private val popularMastersAdapter by lazy { ClientHomePopularMastersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdsAdapter()
        setupRv()
        setupViewModel()
        viewModel.getServices()
        viewModel.getAds()
        observeViewModel()
        adapterItemClick()


        binding.ivBtnNotification.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_notificationFragment)
        }

        binding.etHomeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_masterSearchFragment)

        }
    }

    private fun adapterItemClick() {
        popularMastersAdapter.itemCLickListener = {
            findNavController().navigate(R.id.detailPageFragment)
        }
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.services.collect { it ->
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        serviceAdapter.submitList(it.data)
                        popularServicesAdapter.submitList(it.data)
                        popularMastersAdapter.submitList(it.data)
                        Log.d("@@@", it.data.toString())

                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.ads.collect {
                when (it) {
                    is UiStateList.LOADING -> {
                    }
                    is UiStateList.SUCCESS -> {
                        adsAdapter.submitAds(it.data!!)

                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRv() {
        binding.rvUserHomeService.adapter = serviceAdapter
        binding.rvUserHomePopularServices.adapter = popularServicesAdapter
        binding.rvUserHomePopularMasters.adapter = popularMastersAdapter
    }

    private fun setupAdsAdapter() {
        binding.vpUserHomeAds.adapter = adsAdapter
        binding.vpUserHomeAds.setCurrentItem(0, true)
        binding.userHomeAdsDotsIndicator.setViewPager2(binding.vpUserHomeAds)
        addAutoScrollToViewPager()
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
            MasterHomeViewModelFactory(MasterHomeRepository(ApiClient.createService(ApiService::class.java)))
        )[MasterHomeViewModel::class.java]
    }


    private fun addAutoScrollToViewPager() {

        job = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(3000)
                if (binding.vpUserHomeAds.currentItem == adsAdapter.itemCount - 1) {
                    binding.vpUserHomeAds.currentItem = 0
                } else {
                    binding.vpUserHomeAds.setCurrentItem(binding.vpUserHomeAds.currentItem + 1,
                        true)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()

    }



}