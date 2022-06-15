package com.heymaster.heymaster.role.master.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
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
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.home.Advertising
import com.heymaster.heymaster.model.home.TopMasters
import com.heymaster.heymaster.role.master.adapter.MasterHomeAdsPagerAdapter
import com.heymaster.heymaster.role.master.adapter.MasterHomePopularMasterAdapter
import com.heymaster.heymaster.role.master.adapter.MasterHomePopularServicesAdapter
import com.heymaster.heymaster.role.master.adapter.MasterHomeServicesAdapter
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive

class MasterHomeFragment : BaseFragment(R.layout.fragment_master_home) {

    private val binding by viewBinding { FragmentMasterHomeBinding.bind(it) }

    private var job:Job? = null

    private lateinit var viewModel: MasterHomeViewModel
    private val serviceAdapter by lazy { MasterHomeServicesAdapter() }
    private val adsAdapter by lazy { MasterHomeAdsPagerAdapter() }
    private val popularServicesAdapter by lazy { MasterHomePopularServicesAdapter() }
    private val popularMastersAdapter by lazy { MasterHomePopularMasterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdsAdapter()
        setupRv()
        setupViewModel()
        viewModel.getHome()
        viewModel.getAds()

        observeViewModel()
        adapterItemClick()


        binding.ivBtnNotification.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_notificationFragment)
        }

        binding.etHomeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_masterSearchFragment)
        }

        binding.btnAllServices.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_masterPopularServicesFragment) }

        binding.btnAllPopularMasters.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_masterPopularMasterFragment)
        }

        binding.btnAllPopularServices.setOnClickListener {
            findNavController().navigate(R.id.action_masterHomeFragment_to_masterPopularServicesFragment)
        }
    }

    private fun adapterItemClick() {
        popularMastersAdapter.itemCLickListener = {
            findNavController().navigate(R.id.detailPageFragment)
        }
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.ads.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.nestedHome.visibility = View.GONE
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                        Log.d("@@@loading", "observeViewModel: sdsasad")
                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        binding.nestedHome.visibility = View.VISIBLE
                        val list = ArrayList<Advertising>()
                        list.addAll(listOf(it.data!!))
                        adsAdapter.submitAds(list)

                    }
                    is UiStateObject.ERROR -> {
                        Log.d("@@@", "observeViewModel: ${it.message}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.home.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.nestedHome.visibility = View.GONE
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        binding.nestedHome.visibility = View.VISIBLE
                        serviceAdapter.submitList(it.data.categoryList)
                        popularMastersAdapter.submitList(it.data.topMastersList)
//                        popularServicesAdapter.submitList(it.data.topProfessionList)

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



    private fun setupRv() {
        binding.rvMasterHomeService.adapter = serviceAdapter
        binding.rvMasterHomePopularServices.adapter = popularServicesAdapter
        binding.rvMasterHomePopularMasters.adapter = popularMastersAdapter
    }

    private fun setupAdsAdapter() {
        binding.vpMasterHomeAds.adapter = adsAdapter
        binding.vpMasterHomeAds.setCurrentItem(0, true)
        binding.userHomeAdsDotsIndicator.setViewPager2(binding.vpMasterHomeAds)
        addAutoScrollToViewPager()
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            MasterHomeViewModelFactory(MasterHomeRepository(ApiClient.createServiceWithAuth(ApiService::class.java, token!!)))
        )[MasterHomeViewModel::class.java]
    }

    private fun addAutoScrollToViewPager() {

        job = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(3000)
                if (binding.vpMasterHomeAds.currentItem == adsAdapter.itemCount - 1) {
                    binding.vpMasterHomeAds.currentItem = 0
                } else {
                    binding.vpMasterHomeAds.setCurrentItem(binding.vpMasterHomeAds.currentItem + 1,
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