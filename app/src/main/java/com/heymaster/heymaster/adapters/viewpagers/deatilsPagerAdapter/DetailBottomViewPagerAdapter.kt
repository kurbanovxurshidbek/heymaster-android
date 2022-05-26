package com.heymaster.heymaster.adapters.viewpagers.deatilsPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.heymaster.heymaster.ui.master.notification.messages.NotificationMessagesFragment
import com.heymaster.heymaster.ui.master.notification.suggestion.NotificationSuggestionsFragment

class DetailBottomViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments = ArrayList<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

}