package com.heymaster.heymaster.role.client.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentUserNotificationBinding
import com.heymaster.heymaster.role.client.adapter.ClientNotificationPagerAdapter
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class ClientNotificationFragment : BaseFragment(R.layout.fragment_user_notification) {

    private val binding by viewBinding { FragmentUserNotificationBinding.bind(it) }

    private lateinit var adapter: ClientNotificationPagerAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClientNotificationPagerAdapter(childFragmentManager, lifecycle)

        setupViewPager()



    }

    private fun setupViewPager() {
        adapter.addFragment(ClientNotificationSuggestionsFragment())
        adapter.addFragment(ClientNotificationMessagesFragment())

        binding.vpUserNotifications.adapter = adapter
        binding.vpUserNotifications.setCurrentItem(0, true)

        binding.tabUserNotifications.addTab(binding.tabUserNotifications.newTab().setText("Active"))
        binding.tabUserNotifications.addTab(binding.tabUserNotifications.newTab().setText("History"))

        binding.tabUserNotifications.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpUserNotifications.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.vpUserNotifications.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabUserNotifications.selectTab(binding.tabUserNotifications.getTabAt(position))
            }
        })
    }


}