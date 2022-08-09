package com.heymaster.heymaster.role.client.fragment.profile


import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Picture
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.heymaster.heymaster.App
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.SplashActivity
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.DialogChangeUserRoleBinding
import com.heymaster.heymaster.databinding.DialogChooseLanguageBinding
import com.heymaster.heymaster.databinding.DialogLogOutBinding
import com.heymaster.heymaster.databinding.FragmentUserProfileBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.manager.LocaleManager
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientProfileViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientProfileViewModelFactory
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.heymaster.heymaster.utils.Constants.DEMO_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.DEMO_PHONE_NUMBER
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_DEVICE_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_INTRO_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_PHONE_NUMBER
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.Constants.KEY_VERIFICATION_ID
import com.heymaster.heymaster.utils.Constants.MASTER
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.sink
import java.io.File
import java.io.FileOutputStream


class ClientProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private val binding by viewBinding { FragmentUserProfileBinding.bind(it) }
    private lateinit var viewModel: ClientProfileViewModel

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var attachment = File("")

    private var isAlreadyMaster: Boolean? = null
    private var fullname: String? = null
    private var gender: Boolean? = null
    private var birthDate: String? = null

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListeners()
        setupViewModel()

        viewModel.currentUser()

        binding.tvClientToMaster.setOnClickListener {
            showChangeRoleDialog()
        }

        observeViewModel()



    }

    private fun uploadAttachment() {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        if (attachment.length() > 0) {
            builder.addFormDataPart(
                "file",
                attachment.name,
                attachment.asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
        }
        val body = builder.build()
        viewModel.uploadProfilePhoto(body)


    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.currentUser.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        isAlreadyMaster = it.data.alreadyIsMaster
                        fullname = it.data.fullName
                        gender = it.data.gender
                        //birthDate = it.data.birthDate
                        val currentUser = it.data
                        with(binding) {
                            tvFullname.text = currentUser.fullName
                            etPhoneNumber.text = Editable.Factory.getInstance().newEditable(currentUser.phoneNumber)

                            if (it.data.profilePhoto != null) {
                                Glide.with(binding.ivProfile).load(ATTACHMENT_URL + it.data.profilePhoto!!.id).into(binding.ivProfile)
                            }
                        }
                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uploadProfilePhoto.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()

                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        viewModel.currentUser()
                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.clientToMasterIsAlreadyMaster.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        if (it.data.success) {
                            val activity = context as AppCompatActivity
                            SharedPref(requireContext()).saveString(KEY_USER_ROLE, MASTER)
                            startActivity(Intent(requireContext(), SplashActivity::class.java))
                            activity.finish()
                        }

                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
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
            val activity = context as AppCompatActivity
            SharedPref(requireContext()).removeString(KEY_ACCESS_TOKEN)
            SharedPref(requireContext()).removeString(KEY_PHONE_NUMBER)
            SharedPref(requireContext()).removeString(KEY_LOGIN_SAVED)
            SharedPref(requireContext()).removeString(KEY_CONFIRM_CODE)
            SharedPref(requireContext()).removeString(KEY_VERIFICATION_ID)
            SharedPref(requireContext()).removeString(KEY_DEVICE_TOKEN)
            SharedPref(requireContext()).removeString(DEMO_PHONE_NUMBER)
            SharedPref(requireContext()).removeString(DEMO_CONFIRM_CODE)
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity.finish()
        }

        binding.tvCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun showChangeRoleDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogChangeUserRoleBinding =
            DialogChangeUserRoleBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOk.setOnClickListener {
            if (isAlreadyMaster == true) {
                viewModel.clientToMasterIsAlreadyMaster()
                dialog.dismiss()
            } else {
                findNavController().navigate(R.id.action_userProfileFragment_to_clientToMasterFragment, bundleOf("fullname" to fullname, "gender" to gender, "birthDate" to  birthDate))
                dialog.dismiss()
            }
        }

        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
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

                    val ins = requireActivity().contentResolver.openInputStream(fileUri)
                    attachment = File.createTempFile(
                        "file",
                        ".jpg",
                        requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    )

                    val fileOutputStream = FileOutputStream(attachment)

                    ins?.copyTo(fileOutputStream)
                    ins?.close()
                    fileOutputStream.close()

                    uploadAttachment()

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

        binding.btnUzbek.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_UZBEK)

        }
        binding.btnRussian.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_RUSSIAN)

        }
        binding.btnEnglish.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_ENGLISH)
        }

        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.userProfileFragment)
            dialog.dismiss()

        }
        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}