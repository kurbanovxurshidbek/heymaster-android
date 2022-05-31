package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientNotificationPagerAdapter
import com.heymaster.heymaster.databinding.FragmentNotificationBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterNotificationFragment : BaseFragment(R.layout.fragment_notification) {

    val binding by viewBinding { FragmentNotificationBinding.bind(it) }

    private lateinit var adapter: ClientNotificationPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ClientNotificationPagerAdapter(childFragmentManager, lifecycle)
        setupViewPager()
    }

    private fun setupViewPager() {
        adapter.addFragment(MasterNotificationSuggestionsFragment())
        adapter.addFragment(MasterNotificationMessagesFragment())

        binding.vpNotification.adapter = adapter

        binding.tabNotification.addTab(binding.tabNotification.newTab().setText("Suggestions"))
        binding.tabNotification.addTab(binding.tabNotification.newTab().setText("Messages"))

        binding.tabNotification.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.vpNotification.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.vpNotification.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabNotification.selectTab(binding.tabNotification.getTabAt(position))
            }
        })
    }


}