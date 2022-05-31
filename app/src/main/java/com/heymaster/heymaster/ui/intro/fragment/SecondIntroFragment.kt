package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentSecondIntroBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class SecondIntroFragment : BaseFragment(R.layout.fragment_second_intro) {


    private val binding by viewBinding { FragmentSecondIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}