package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentThirdIntroBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class ThirdIntroFragment : BaseFragment(R.layout.fragment_third_intro) {

    private val binding by viewBinding { FragmentThirdIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}