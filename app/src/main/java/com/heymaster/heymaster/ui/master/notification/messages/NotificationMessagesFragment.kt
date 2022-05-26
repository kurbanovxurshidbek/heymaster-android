package com.heymaster.heymaster.ui.master.notification.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.notification.NotificationMessagesAdapter
import com.heymaster.heymaster.adapters.notification.NotificationSuggestionAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentNotificationMessagesBinding
import com.heymaster.heymaster.databinding.FragmentNotificationSuggensionsBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.ui.master.notification.suggestion.NotificationSuggestionRepository
import com.heymaster.heymaster.ui.master.notification.suggestion.NotificationSuggestionViewModel
import com.heymaster.heymaster.ui.master.notification.suggestion.NotificationSuggestionViewModelFactory
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class NotificationMessagesFragment : BaseFragment(R.layout.fragment_notification_messages) {

    val binding by viewBinding { FragmentNotificationMessagesBinding.bind(it) }
    private val notificationAdapter by lazy { NotificationMessagesAdapter()  }
    private lateinit var viewModel: NotificationMessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        viewModel.getNotifications()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.notifications.collect(){
                when(it){
                    is UiStateList.LOADING ->{

                    }
                    is UiStateList.SUCCESS -> {
                        notificationAdapter.submitList(it.data)
                    }
                    is  UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, NotificationMessagesViewModelFactory(
            NotificationMessagesRepository(
            ApiClient.createService(ApiService::class.java)
        )
        )
        )[NotificationMessagesViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvNotifMessages.adapter = notificationAdapter
    }

}