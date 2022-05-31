package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.master.adapter.DetailBottomViewPagerAdapter
import com.heymaster.heymaster.databinding.FragmentDetailPageBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class DetailPageFragment : BaseFragment(R.layout.fragment_detail_page) {

    private val binding by viewBinding { FragmentDetailPageBinding.bind(it) }
    private val adapter by lazy { DetailBottomViewPagerAdapter(childFragmentManager, lifecycle) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
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