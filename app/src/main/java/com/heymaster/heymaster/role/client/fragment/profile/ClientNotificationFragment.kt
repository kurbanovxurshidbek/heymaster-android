package com.heymaster.heymaster.role.client.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentUserNotificationBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.adapter.ClientNotificationsAdapter
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect

class ClientNotificationFragment : BaseFragment(R.layout.fragment_user_notification) {

    private val binding by viewBinding { FragmentUserNotificationBinding.bind(it) }
    private lateinit var viewModel: ClientHomeViewModel

    private val notificationAdapter by lazy { ClientNotificationsAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()

        viewModel.getNotifications()
        observeViewModel()



    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.clientNotifications.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        Log.d("TAG@@@", "observeViewModel: ${it.data}")
                        notificationAdapter.submitList(it.data.`object`)

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientHomeViewModelFactory(ClientHomeRepository(ApiClient.createServiceWithAuth(
                ApiService::class.java, token!!)))
        )[ClientHomeViewModel::class.java]
    }

    private fun setupRv() {
        binding.rvClientNotification.adapter = notificationAdapter
    }


}