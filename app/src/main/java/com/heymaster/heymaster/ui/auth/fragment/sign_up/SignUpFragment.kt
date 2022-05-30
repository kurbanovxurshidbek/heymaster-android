package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout.*
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.viewpagers.SignUpPagerAdapter
import com.heymaster.heymaster.databinding.FragmentSignUpBinding
import com.heymaster.heymaster.ui.auth.AuthSharedViewModel
import com.heymaster.heymaster.utils.extensions.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding { FragmentSignUpBinding.bind(it) }
    private lateinit var viewModel: AuthSharedViewModel
    private lateinit var adapter: SignUpPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        adapter = SignUpPagerAdapter(childFragmentManager, lifecycle)
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
//                        startActivity(Intent(requireActivity(), UserActivity::class.java))
//                        activity?.finish()
                        viewModel.clickListener(position)
                    } else {
//                        startActivity(Intent(requireActivity(), MasterActivity::class.java))
//                        activity?.finish()
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(AuthSharedViewModel::class.java)
    }

}