package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentServiceDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class ClientServiceDetailFragment : BaseFragment(R.layout.fragment_service_detail) {

    private val binding by viewBinding { FragmentServiceDetailBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}