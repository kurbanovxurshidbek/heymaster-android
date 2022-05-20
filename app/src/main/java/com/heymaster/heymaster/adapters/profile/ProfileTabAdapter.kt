package com.heymaster.heymaster.adapters.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.heymaster.heymaster.ui.master.fragment.profile.MasterChildProfileFragment
import com.heymaster.heymaster.ui.master.fragment.profile.MasterPortfolioFragment


//This Adapter do control Fragments in Master Profile
class ProfileTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                MasterPortfolioFragment()
            }
            1 -> {
                MasterChildProfileFragment()
            }
            else -> Fragment()
        }
    }


}