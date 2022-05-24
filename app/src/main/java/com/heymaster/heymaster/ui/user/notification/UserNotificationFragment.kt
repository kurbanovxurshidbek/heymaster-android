package com.heymaster.heymaster.ui.user.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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