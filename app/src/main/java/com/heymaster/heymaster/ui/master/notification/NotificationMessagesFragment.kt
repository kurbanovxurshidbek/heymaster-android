package com.heymaster.heymaster.ui.master.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.notification.NotificationMessagesAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentNotificationMessagesBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class NotificationMessagesFragment : BaseFragment(R.layout.fragment_notification_messages) {

    val binding by viewBinding { FragmentNotificationMessagesBinding.bind(it) }
    private val notificationAdapter by lazy { NotificationMessagesAdapter()  }

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