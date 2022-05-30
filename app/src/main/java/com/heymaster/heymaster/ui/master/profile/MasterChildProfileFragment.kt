package com.heymaster.heymaster.ui.master.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterChildProfileBinding
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterChildProfileFragment : Fragment(R.layout.fragment_master_child_profile) {
    private val binding by viewBinding { FragmentMasterChildProfileBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}