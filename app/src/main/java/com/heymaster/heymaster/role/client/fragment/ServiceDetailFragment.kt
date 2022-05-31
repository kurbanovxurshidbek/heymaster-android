package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentServiceDetailBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class ServiceDetailFragment : BaseFragment(R.layout.fragment_service_detail) {

    private val binding by viewBinding { FragmentServiceDetailBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}