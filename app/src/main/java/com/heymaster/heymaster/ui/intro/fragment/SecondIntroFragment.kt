package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentSecondIntroBinding
import com.heymaster.heymaster.databinding.FragmentSignUpBinding
import com.heymaster.heymaster.utils.extensions.viewBinding


class SecondIntroFragment : Fragment(R.layout.fragment_second_intro) {


    private val binding by viewBinding { FragmentSecondIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}