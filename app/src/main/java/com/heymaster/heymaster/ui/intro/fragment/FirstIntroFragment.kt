package com.heymaster.heymaster.ui.intro.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentFirstIntroBinding
import com.heymaster.heymaster.utils.extensions.viewBinding


class FirstIntroFragment : Fragment(R.layout.fragment_first_intro) {

    private val binding by viewBinding { FragmentFirstIntroBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}