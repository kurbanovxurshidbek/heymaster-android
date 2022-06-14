package com.heymaster.heymaster.role.client.fragment


import android.annotation.SuppressLint

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.DialogChooseLanguageBinding
import com.heymaster.heymaster.databinding.DialogLogOutBinding
import com.heymaster.heymaster.databinding.FragmentUserProfileBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.repository.ClientHomeRepository
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientHomeViewModel
import com.heymaster.heymaster.role.client.viewmodel.ClientProfileViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientHomeViewModelFactory
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientProfileViewModelFactory
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_PHONE_NUMBER
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ClientProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private val binding by viewBinding { FragmentUserProfileBinding.bind(it) }
    private lateinit var viewModel: ClientProfileViewModel

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()

        setupViewModel()
        viewModel.currentUser()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.currentUser.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        val currentUser = it.data
                        with(binding) {
                            tvFullname.text = currentUser.fullName
                        }

                    }
                    is UiStateObject.ERROR -> {
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientProfileViewModelFactory(ClientProfileRepository(ApiClient.createServiceWithAuth(ApiService::class.java,token!!)))
        )[ClientProfileViewModel::class.java]
    }

    private fun clickListeners() {

        binding.apply{
            btnUserNotification.setOnClickListener {
                findNavController().navigate(R.id.action_userProfileFragment_to_userNotificationFragment)
            }
            btnUserChangeLanguage.setOnClickListener {
                showChooseLanguageDialog()
            }
            btnUserAboutUs.setOnClickListener {
                findNavController().navigate(R.id.action_userProfileFragment_to_clientAboutFragment)
            }

            btnUserHelp.setOnClickListener {
                findNavController().navigate(R.id.action_userProfileFragment_to_clientProfileHelpFragment)
            }
            btnUserLogOut.setOnClickListener {
                showLogOutDialog()
            }
        }

        with(binding) {
            ivProfile.setOnClickListener {
                pickImageProfile()
            }
            ivEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_userProfileFragment_to_clientEditProfileFragment)
            }

        }
    }

    private fun showLogOutDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogLogOutBinding =
            DialogLogOutBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOk.setOnClickListener {
            SharedPref(requireContext()).removeString(KEY_ACCESS_TOKEN)
            SharedPref(requireContext()).removeString(KEY_PHONE_NUMBER)
            SharedPref(requireContext()).removeString(KEY_LOGIN_SAVED)
            SharedPref(requireContext()).removeString(KEY_CONFIRM_CODE)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        binding.tvCancel.setOnClickListener {
            dialog.show()
        }
        dialog.show()
    }


    private fun pickImageProfile() {
        ImagePicker.with(this)
            .compress(1024)
            .crop()
            .galleryMimeTypes(mimeTypes = arrayOf(
                "image/png",
                "image/jpg",
                "image/jpeg"
            ))//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080,
                1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    binding.ivProfile.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun showChooseLanguageDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogChooseLanguageBinding =
            DialogChooseLanguageBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}