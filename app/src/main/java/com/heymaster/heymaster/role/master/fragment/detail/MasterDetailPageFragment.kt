package com.heymaster.heymaster.role.master.fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentDetailPageMasterBinding
import com.heymaster.heymaster.role.master.adapter.DetailBottomViewPagerAdapter
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.repository.DetailsRepository
import com.heymaster.heymaster.role.master.viewmodel.DetailsViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.DetailsViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect


class MasterDetailPageFragment : BaseFragment(R.layout.fragment_detail_page_master) {

    private val binding by viewBinding { FragmentDetailPageMasterBinding.bind(it) }
    private val adapter by lazy { DetailBottomViewPagerAdapter(childFragmentManager, lifecycle) }
    private lateinit var viewModel: DetailsViewModel

    private var id: Int? = null

    private var phoneNumber: String? = null
    private var fullname: String? = null
    private var masterRegion: String? = null
    private var photoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("master_id")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViewPager()

        id?.let {
            viewModel.getMasterDetail(id!!)
        }

        observeViewModel()

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }

        binding.directionBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/$masterRegion"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        binding.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                putExtra(Intent.EXTRA_TEXT, photoUrl)
                type = "text/*"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterProfile.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                        Log.d("@@@loading", "observeViewModel: loading")
                    }
                    is UiStateObject.SUCCESS -> {
                        Log.d("@@@success", "observeViewModel: loading")
                        val detailMaster = it.data
                        dismissLoading()
                        binding.detailFullName.text = detailMaster.`object`.fullName
                        binding.tvDetailDistrict.text = detailMaster.`object`.location.district.nameUz
                        binding.tvDetailRegion.text = detailMaster.`object`.location.region.nameUz
                        binding.totalMark.text = detailMaster.`object`.totalMark.toString()

                        if(it.data.`object`.busy){
                            binding.isBusy.text = resources.getString(R.string.open)
                            binding.busyCard.setCardBackgroundColor(R.color.green1)
                        }else {
                            binding.isBusy.text = resources.getString(R.string.close)
                            binding.busyCard.setCardBackgroundColor(R.color.red)

                        }

                        fullname = it.data.`object`.fullName
                        phoneNumber = it.data.`object`.phoneNumber
                        photoUrl = it.data.`object`.profilePhoto
                        masterRegion = it.data.`object`.location.region.nameUz

                        if(it.data.`object`.peopleReitedCount != 0) {
                            binding.ratingBar.rating = (it.data.`object`.totalMark/it.data.`object`.peopleReitedCount).toFloat()
                        } else {
                            binding.ratingBar.rating = 0.toFloat()
                        }

                        if (it.data.`object`.profilePhoto != null) {
                            Picasso.get().load(it.data.`object`.profilePhoto).into(binding.detailProfileImage)
                        } else {
                            binding.detailProfileImage.setImageResource(R.drawable.img_2)
                        }

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

        binding.detailBottomViewPager.setCurrentItem(0, true)

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