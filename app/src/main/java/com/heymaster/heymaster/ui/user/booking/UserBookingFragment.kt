package com.heymaster.heymaster.ui.user.booking

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentUserBookingBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.adapters.viewpagers.BookingsPagerAdapter
import com.heymaster.heymaster.utils.extensions.viewBinding


class UserBookingFragment : BaseFragment(R.layout.fragment_user_booking) {

    private val binding by viewBinding { FragmentUserBookingBinding.bind(it) }

    private lateinit var adapter: BookingsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BookingsPagerAdapter(childFragmentManager, lifecycle)
        setupViewPager()

    }

    private fun setupViewPager() {
        adapter.addFragment(UserActiveBookingFragment())
        adapter.addFragment(UserHistoryBookingFragment())

        binding.vpUserBookings.adapter = adapter
        binding.vpUserBookings.setCurrentItem(0, true)

        binding.tabUserBookings.addTab(binding.tabUserBookings.newTab().setText("Active"))
        binding.tabUserBookings.addTab(binding.tabUserBookings.newTab().setText("History"))

        binding.tabUserBookings.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpUserBookings.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.vpUserBookings.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabUserBookings.selectTab(binding.tabUserBookings.getTabAt(position))
            }
        })
    }
    }


