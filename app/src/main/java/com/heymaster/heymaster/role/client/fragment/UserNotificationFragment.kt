package com.heymaster.heymaster.role.client.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentUserNotificationBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class UserNotificationFragment : BaseFragment(R.layout.fragment_user_notification) {

    private val binding by viewBinding { FragmentUserNotificationBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}