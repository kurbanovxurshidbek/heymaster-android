package com.heymaster.heymaster.role.client.fragment

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
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularMastersAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeProfessionsAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeCategoryAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeAdsPagerAdapter
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserHomeBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.home.Advertising
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive


class ClientHomeFragment : BaseFragment(R.layout.fragment_user_home) {

    private val binding by viewBinding { FragmentUserHomeBinding.bind(it) }
    private var job: Job? = null

    private lateinit var viewModel: ClientHomeViewModel
    private val categoryAdapter by lazy { ClientHomeCategoryAdapter() }
    private val adsAdapter by lazy { ClientHomeAdsPagerAdapter() }
    private val professionsAdapter by lazy { ClientHomeProfessionsAdapter() }
    private val popularMastersAdapter by lazy { ClientHomePopularMastersAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdsAdapter()
        setupRv()
        setupViewModel()
        viewModel.getHome()
        viewModel.getAds()
        observeViewModel()
        adapterItemClick()
        clickListeners()



    }

    private fun adapterItemClick() {

        categoryAdapter.itemClickListener = {
            launchCategoryDetailFragment(it.id)
        }


    }

    private fun launchCategoryDetailFragment(id: Int) {
        findNavController().navigate(R.id.action_userHomeFragment_to_serviceDetailFragment, bundleOf("category_id" to id))

    }

    private fun clickListeners() {
        with(binding) {
            ivBtnNotification.setOnClickListener {
                findNavController().navigate(R.id.action_userHomeFragment_to_userNotificationFragment)
            }

            etHomeSearch.setOnClickListener {
                findNavController().navigate(R.id.action_userHomeFragment_to_userSearchFragment)
            }

            btnAllServices.setOnClickListener {
                findNavController().navigate(R.id.action_userHomeFragment_to_userAllServicesFragment)
            }

            btnAllPopularServices.setOnClickListener {
                findNavController().navigate(R.id.action_userHomeFragment_to_userAllPopularServiceFragment)
            }

            btnAllPopularMasters.setOnClickListener {
                findNavController().navigate(R.id.action_userHomeFragment_to_userAllPopularMastersFragment)
            }
        }





    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.ads.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
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
                        categoryAdapter.submitList(it.data.categoryList)
                        professionsAdapter.submitList(it.data.topProfessionList)
                        popularMastersAdapter.submitList(it.data.topMastersList)



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
        binding.rvUserHomeService.adapter = categoryAdapter
        binding.rvUserHomePopularServices.adapter = professionsAdapter
        binding.rvUserHomePopularMasters.adapter = popularMastersAdapter

        popularMastersAdapter.itemCLickListener = {
            findNavController().navigate(R.id.action_userHomeFragment_to_detailPageFragment2)
        }
    }

    private fun setupAdsAdapter() {
        binding.vpUserHomeAds.adapter = adsAdapter

        binding.vpUserHomeAds.setCurrentItem(0, true)
        binding.userHomeAdsDotsIndicator.setViewPager2(binding.vpUserHomeAds)
        addAutoScrollToViewPager()
    }


    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createServiceWithAuth(ApiService::class.java, token!!)))
        )[ClientHomeViewModel::class.java]
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