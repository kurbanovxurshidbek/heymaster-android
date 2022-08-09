package com.heymaster.heymaster.role.master.fragment.profile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.SplashActivity
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.DialogChangeUserRoleBinding
import com.heymaster.heymaster.ui.adapter.MasterProfilePagerAdapter
import com.heymaster.heymaster.databinding.FragmentMasterProfileBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.role.master.repository.MasterProfileRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterProfileViewModelFactory
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.heymaster.heymaster.utils.Constants.CLIENT
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.Constants.MASTER
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class MasterProfileFragment : BaseFragment(R.layout.fragment_master_profile) {

    private val binding by viewBinding { FragmentMasterProfileBinding.bind(it) }
    lateinit var adapter: MasterProfilePagerAdapter
    private lateinit var viewModel: MasterProfileViewModel

    private var attachment = File("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViewPager()
        itemClickManager()
        viewModel.getMasterProfile()
        observeViewModel()
    }

    private fun itemClickManager() {
        binding.ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.masterEditProfileFragment)
        }

        binding.tvMasterToClient.setOnClickListener {
            showChangeRoleDialog()
        }

        binding.apply {

            ivProfile.setOnClickListener {
                pickImageProfile()
            }
            ivEditProfile.setOnClickListener {
                findNavController().navigate(R.id.masterEditProfileFragment)
            }

        }
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterProfile.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        val profileMaster = it.data
                        with(binding) {
                            tvProfile.text = profileMaster.fullName
                            tvProfileProvince.text = profileMaster.location.district.nameUz
                            tvProfileRegion.text = profileMaster.location.region.nameUz
                            if (it.data.profilePhoto != null) {
                                Glide.with(ivProfile).load(ATTACHMENT_URL + it.data.profilePhoto.id).into(binding.ivProfile)
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
            viewModel.masterToClient.collect {
                when(it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        if(it.data.success) {
                            val activity = context as AppCompatActivity
                            SharedPref(requireContext()).saveString(KEY_USER_ROLE, CLIENT)
                            startActivity(Intent(requireContext(), SplashActivity::class.java))
                            activity.finish()
                        }
                    }
                    is UiStateObject.ERROR -> {
                        showLoading()
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
                        viewModel.getMasterProfile()
                    }
                    is UiStateObject.ERROR -> {
                        dismissLoading()
                    }
                    else -> Unit
                }
            }
        }
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


    private fun pickImageProfile() {
        ImagePicker.with(this)
            .compress(1024)
            .crop()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )//Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
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
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this, MasterProfileViewModelFactory(
                MasterProfileRepository(
                    ApiClient.createServiceWithAuth(
                        ApiService::class.java, token!!

                    )
                )
            )
        )[MasterProfileViewModel::class.java]
    }

    private fun setupViewPager() {
        adapter = MasterProfilePagerAdapter(childFragmentManager, lifecycle)
        binding.apply {
            tabMasterProfile.addTab(binding.tabMasterProfile.newTab().setText("Portfolio"))
            tabMasterProfile.addTab(binding.tabMasterProfile.newTab().setText("Profile"))
            vpMasterProfile.adapter = adapter
        }

        binding.tabMasterProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpMasterProfile.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.vpMasterProfile.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabMasterProfile.selectTab(binding.tabMasterProfile.getTabAt(position))

            }
        })
    }


    private fun showChangeRoleDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogChangeUserRoleBinding =
            DialogChangeUserRoleBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvTitle.text = "Oddiy foydalanuvchi sifatida davom etasizmi ?"

        binding.tvOk.setOnClickListener {
            viewModel.masterToClient()
            dialog.dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}

