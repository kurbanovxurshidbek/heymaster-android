package com.heymaster.heymaster.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.heymaster.heymaster.role.master.fragment.profile.MasterChildProfileFragment
import com.heymaster.heymaster.ui.master.profile.MasterPortfolioFragment

class MasterProfilePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MasterPortfolioFragment()
            1 -> MasterChildProfileFragment()
            else -> Fragment()
        }
    }
}