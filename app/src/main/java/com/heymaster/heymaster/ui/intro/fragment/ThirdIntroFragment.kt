package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentSecondIntroBinding
import com.heymaster.heymaster.databinding.FragmentThirdIntroBinding
import com.heymaster.heymaster.utils.viewBinding


class ThirdIntroFragment : Fragment(R.layout.fragment_third_intro) {

    private val binding by viewBinding { FragmentThirdIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}