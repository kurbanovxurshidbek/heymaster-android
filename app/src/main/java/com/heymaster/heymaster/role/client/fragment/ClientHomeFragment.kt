package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularMastersAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularServicesAdapter
import com.heymaster.heymaster.role.client.adapter.ClientHomeServicesAdapter
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
import com.heymaster.heymaster.utils.UiStateList
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
    private val serviceAdapter by lazy { ClientHomeServicesAdapter() }
    private val adsAdapter by lazy { ClientHomeAdsPagerAdapter() }
    private val popularServicesAdapter by lazy { ClientHomePopularServicesAdapter() }
    private val popularMastersAdapter by lazy { ClientHomePopularMastersAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        popularMastersAdapter.itemCLickListener = {
            findNavController().navigate(R.id.detailPageFragment2)
        }

        serviceAdapter.itemClickListener = {
            findNavController().navigate(R.id.serviceDetailFragment)
        }

        popularServicesAdapter.itemClickListener = {
            findNavController().navigate(R.id.serviceDetailFragment)
        }
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

//        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//            viewModel.services.collect { it ->
//                when (it) {
//                    is UiStateList.LOADING -> {
////                        binding.nestedHome.visibility = View.GONE
////                        binding.progressHome.customProgress.visibility = View.VISIBLE
//                    }
//                    is UiStateList.SUCCESS -> {
////                        binding.progressHome.customProgress.visibility = View.GONE
////                        binding.nestedHome.visibility = View.VISIBLE
////                        serviceAdapter.submitList(it.data)
////                        popularServicesAdapter.submitList(it.data)
////                        popularMastersAdapter.submitList(it.data)
////                        Log.d("@@@", it.data.toString())
//
//                    }
//                    is UiStateList.ERROR -> {
//                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                    }
//                    else -> Unit
//                }
//            }
//        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.ads.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.nestedHome.visibility = View.GONE
                        binding.progressHome.customProgress.visibility = View.VISIBLE
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
        binding.rvUserHomeService.adapter = serviceAdapter
        binding.rvUserHomePopularServices.adapter = popularServicesAdapter
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