package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentSignUpBinding
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterSignUpFragment : Fragment(R.layout.fragment_master_sign_up) {

    private val binding by viewBinding { FragmentSignUpBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}