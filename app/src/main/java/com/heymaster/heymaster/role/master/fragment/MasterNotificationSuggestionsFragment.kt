package com.heymaster.heymaster.role.master.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.client.adapter.ClientNotificationSuggestionAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentNotificationSuggensionsBinding
import com.heymaster.heymaster.role.master.repository.MasterNotificationRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterNotificationViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterNotificationViewModelFactory
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class MasterNotificationSuggestionsFragment : BaseFragment(R.layout.fragment_notification_suggensions) {

    val binding by viewBinding { FragmentNotificationSuggensionsBinding.bind(it) }
    private val notificationAdapter by lazy { ClientNotificationSuggestionAdapter()  }
    private lateinit var viewModel: MasterNotificationViewModel

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
       viewModel = ViewModelProvider(this, MasterNotificationViewModelFactory(
           MasterNotificationRepository(
           ApiClient.createService(ApiService::class.java)
       )))[MasterNotificationViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvNotifSuggestion.adapter = notificationAdapter
    }


}