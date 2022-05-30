package com.heymaster.heymaster.ui.master.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.notification.NotificationSuggestionAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentNotificationSuggensionsBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class NotificationSuggestionsFragment : BaseFragment(R.layout.fragment_notification_suggensions) {

    val binding by viewBinding { FragmentNotificationSuggensionsBinding.bind(it) }
    private val notificationAdapter by lazy {NotificationSuggestionAdapter()  }
    private lateinit var viewModel: NotificationViewModel

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
       viewModel = ViewModelProvider(this, NotificationViewModelFactory(NotificationRepository(
           ApiClient.createService(ApiService::class.java)
       )))[NotificationViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvNotifSuggestion.adapter = notificationAdapter
    }


}