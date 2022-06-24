package com.heymaster.heymaster.role.master.fragment.booking

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientBookingsPagerAdapter
import com.heymaster.heymaster.databinding.FragmentMasterBookingBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterBookingFragment : BaseFragment(R.layout.fragment_master_booking) {

    private val binding by viewBinding { FragmentMasterBookingBinding.bind(it) }
    private lateinit var adapter: ClientBookingsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ClientBookingsPagerAdapter(childFragmentManager,lifecycle)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        adapter.addFragment(MasterActiveBookingFragment())
        adapter.addFragment(MasterHistoryBookingFragment())

        binding.vpMasterBookings.adapter = adapter
        binding.vpMasterBookings.setCurrentItem(0,true)


        binding.tabMasterBookings.addTab(binding.tabMasterBookings.newTab().setText(getString(R.string.active)))
        binding.tabMasterBookings.addTab(binding.tabMasterBookings.newTab().setText(getString(R.string.history)))

        binding.tabMasterBookings.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpMasterBookings.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.vpMasterBookings.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabMasterBookings.selectTab(binding.tabMasterBookings.getTabAt(position))
            }
        })
    }


}