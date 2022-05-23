package com.heymaster.heymaster.ui.master.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.profile.ProfileTabAdapter
import com.heymaster.heymaster.adapters.viewpagers.MasterProfilePagerAdapter
import com.heymaster.heymaster.databinding.FragmentMasterProfileBinding
import com.heymaster.heymaster.ui.user.booking.UserActiveBookingFragment
import com.heymaster.heymaster.ui.user.booking.UserHistoryBookingFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterProfileFragment : Fragment(R.layout.fragment_master_profile) {

    private val binding by viewBinding { FragmentMasterProfileBinding.bind(it) }
    private val adapter by lazy { MasterProfilePagerAdapter(childFragmentManager, lifecycle) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

    }

    private fun setupViewPager() {
        adapter.addFragment(MasterPortfolioFragment())
        adapter.addFragment(MasterChildProfileFragment())

        binding.vpMasterProfile.adapter = adapter
        binding.vpMasterProfile.setCurrentItem(0, true)

        binding.tabMasterProfile.addTab(binding.tabMasterProfile.newTab().setText("Portfolio"))
        binding.tabMasterProfile.addTab(binding.tabMasterProfile.newTab().setText("Profile"))

        binding.tabMasterProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpMasterProfile.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.vpMasterProfile.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabMasterProfile.selectTab(binding.tabMasterProfile.getTabAt(position))

            }
        })
    }
    }

