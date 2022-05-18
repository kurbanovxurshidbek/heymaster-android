package com.heymaster.heymaster.ui.auth.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentVerificationBinding
import com.heymaster.heymaster.utils.extensions.viewBinding


class VerificationFragment : Fragment(R.layout.fragment_verification) {

    private val binding by viewBinding { FragmentVerificationBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}