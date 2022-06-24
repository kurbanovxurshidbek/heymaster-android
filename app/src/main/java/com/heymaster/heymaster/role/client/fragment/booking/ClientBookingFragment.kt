package com.heymaster.heymaster.role.client.fragment.booking

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentUserBookingBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.adapter.ClientBookingsPagerAdapter
import com.heymaster.heymaster.utils.extensions.viewBinding


class ClientBookingFragment : BaseFragment(R.layout.fragment_user_booking) {

    private val binding by viewBinding { FragmentUserBookingBinding.bind(it) }

    private lateinit var adapter: ClientBookingsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ClientBookingsPagerAdapter(childFragmentManager, lifecycle)
        setupViewPager()

        setupUI()


    }

    private fun setupUI() {

    }

    private fun setupViewPager() {
        adapter.addFragment(ClientActiveBookingFragment())
        adapter.addFragment(ClientHistoryBookingFragment())

        binding.vpUserBookings.adapter = adapter
        binding.vpUserBookings.setCurrentItem(0, true)

        binding.tabUserBookings.addTab(binding.tabUserBookings.newTab().setText(getString(R.string.active)))
        binding.tabUserBookings.addTab(binding.tabUserBookings.newTab().setText(getString(R.string.history)))

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


