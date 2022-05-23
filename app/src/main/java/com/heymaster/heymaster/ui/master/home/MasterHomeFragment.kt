package com.heymaster.heymaster.ui.master.home

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterHomeBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterHomeFragment : BaseFragment(R.layout.fragment_master_home) {

    private val binding by viewBinding { FragmentMasterHomeBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}