package com.heymaster.heymaster.role.master.fragment.profile

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.role.master.adapter.MasterPortfolioAdapter
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentMasterPortfolioBinding
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterProfileViewModelFactory
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.role.master.repository.MasterPortfolioRepository
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class MasterPortfolioFragment : BaseFragment(R.layout.fragment_master_portfolio) {
    private val binding by viewBinding { FragmentMasterPortfolioBinding.bind(it) }
    private lateinit var viewModel: MasterProfileViewModel
    private val masterPortfolioAdapter by lazy { MasterPortfolioAdapter() }

    private var attachment = File("")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupViewModel()
        viewModel.attachmentInfo()
        observeViewModel()


        masterPortfolioAdapter.addItemClickListener {
            pickImageProfile()
        }

        masterPortfolioAdapter.imageItemClickListener {
            findNavController().navigate(R.id.action_masterProfileFragment_to_portfolioImageDetailFragment, bundleOf("image_id" to it))
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.attachmentInfo.collect {
                when(it) {
                    is UiStateList.LOADING -> {
                        binding.progressBarPortfolio.isVisible = true
                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressBarPortfolio.isVisible = false
                        val list = ArrayList<Portfolio>()
                        list.add(Portfolio.Add("Alisher"))
                        it.data!!.reversed().forEach {
                            list.add(Portfolio.Image(it))
                        }
                        masterPortfolioAdapter.submitList(list.toList())
                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uploadAttachment.collect {
                when(it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        viewModel.attachmentInfo()

                    }
                    is UiStateObject.ERROR -> {
                        Log.d("errorr", "observeViewModel: $it")
                        //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun setupRv() {
        binding.recyclerViewPortfolio.adapter = masterPortfolioAdapter
    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            MasterProfileViewModelFactory(MasterPortfolioRepository(ApiClient.createServiceWithAuth(ApiService::class.java,token!!)))
        )[MasterProfileViewModel::class.java]
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
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task cancelled", Toast.LENGTH_SHORT).show()
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
        viewModel.uploadAttachment(body)


    }

    override fun onResume() {
        Thread.sleep(500)
        viewModel.attachmentInfo()
        super.onResume()
    }

}