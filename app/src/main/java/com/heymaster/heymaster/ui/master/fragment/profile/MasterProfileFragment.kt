package com.heymaster.heymaster.ui.master.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.profile.ProfileTabAdapter
import com.heymaster.heymaster.databinding.FragmentMasterActiveBookingBinding
import com.heymaster.heymaster.databinding.FragmentMasterProfileBinding
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterProfileFragment : Fragment(R.layout.fragment_master_profile) {

    private lateinit var portfolioAdapter: ProfileTabAdapter

    private val binding by viewBinding { FragmentMasterProfileBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        
        portfolioAdapter = ProfileTabAdapter(childFragmentManager, lifecycle)

        binding.viewPagerProfile.adapter = portfolioAdapter

        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText("Portfolio"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText("Profile"))

        binding.tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPagerProfile.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.viewPagerProfile.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayoutMain.selectTab(binding.tabLayoutMain.getTabAt(position))
            }
        })
    }


}