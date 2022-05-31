package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentFirstIntroBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class FirstIntroFragment : BaseFragment(R.layout.fragment_first_intro) {

    private val binding by viewBinding { FragmentFirstIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}