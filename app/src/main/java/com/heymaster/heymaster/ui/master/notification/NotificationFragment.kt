package com.heymaster.heymaster.ui.master.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.viewpagers.NotificationPagerAdapter
import com.heymaster.heymaster.databinding.FragmentNotificationBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.ui.user.booking.UserActiveBookingFragment
import com.heymaster.heymaster.ui.user.booking.UserHistoryBookingFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class NotificationFragment() : BaseFragment(R.layout.fragment_notification) {

    val binding by viewBinding { FragmentNotificationBinding.bind(it) }

    private lateinit var adapter: NotificationPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        adapter.addFragment(NotificationSuggestionsFragment())
        adapter.addFragment(NotificationMessagesFragment())

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