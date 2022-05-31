package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientNotificationMessagesAdapter
import com.heymaster.heymaster.databinding.FragmentNotificationMessagesBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class MasterNotificationMessagesFragment : BaseFragment(R.layout.fragment_notification_messages) {

    val binding by viewBinding { FragmentNotificationMessagesBinding.bind(it) }
    private val notificationAdapter by lazy { ClientNotificationMessagesAdapter()  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        observeViewModel()
    }

    private fun observeViewModel() {

    }

    private fun setupViewModel() {

    }

    private fun setupRv() {
        binding.rvNotifMessages.adapter = notificationAdapter
    }

}