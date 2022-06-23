package com.heymaster.heymaster.role.master.fragment.profile

import android.os.Bundle

import android.view.View
import com.bumptech.glide.Glide
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentPortfolioImageDetailBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.heymaster.heymaster.utils.RandomColor
import com.heymaster.heymaster.utils.extensions.viewBinding



class PortfolioImageDetailFragment : BaseFragment(R.layout.fragment_portfolio_image_detail) {

    private var imageId: Int? = null

    private val binding by viewBinding { FragmentPortfolioImageDetailBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageId = it.getInt("image_id")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(requireContext()).load(ATTACHMENT_URL + imageId).placeholder(RandomColor.randomColor()).into(binding.ivImage)

    }

}