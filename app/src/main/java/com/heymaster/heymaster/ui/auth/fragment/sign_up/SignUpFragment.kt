package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.*
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentSignUpBinding
import com.heymaster.heymaster.ui.master.MasterActivity
import com.heymaster.heymaster.ui.user.UserActivity
import com.heymaster.heymaster.utils.extensions.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding { FragmentSignUpBinding.bind(it) }
    private lateinit var adapter: SignUpAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SignUpAdapter(childFragmentManager, lifecycle)
        setupViewPager()

    }
    private fun setupViewPager() {
        adapter.addFragment(UserSignUpFragment())
        adapter.addFragment(MasterSignUpFragment())

        binding.viewPagerSignUp.adapter = adapter
        binding.viewPagerSignUp.setCurrentItem(0, true)

        binding.tabSignUp.addTab(binding.tabSignUp.newTab().setText("Client"))
        binding.tabSignUp.addTab(binding.tabSignUp.newTab().setText("Master"))

        binding.tabSignUp.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                binding.viewPagerSignUp.currentItem = tab.position
            }

            override fun onTabUnselected(tab: Tab?) {}

            override fun onTabReselected(tab: Tab?) {}

        })

        binding.viewPagerSignUp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabSignUp.selectTab(binding.tabSignUp.getTabAt(position))
                binding.btnSignUp.setOnClickListener {
                    if (position == 0) {
                        startActivity(Intent(requireActivity(), UserActivity::class.java))
                        activity?.finish()
                    } else {
                        startActivity(Intent(requireActivity(), MasterActivity::class.java))
                        activity?.finish()
                    }
                }
            }
        })
    }

}