package com.heymaster.heymaster.role.client.fragment.profile

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentClientEditProfileBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.editmasterprofile.EditClientRequest
import com.heymaster.heymaster.model.editmasterprofile.EditMasterRequest
import com.heymaster.heymaster.role.client.repository.ClientProfileRepository
import com.heymaster.heymaster.role.client.viewmodel.ClientProfileViewModel
import com.heymaster.heymaster.role.client.viewmodel.factory.ClientProfileViewModelFactory
import com.heymaster.heymaster.role.master.repository.MasterProfileRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterProfileViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*


class ClientEditProfileFragment : BaseFragment(R.layout.fragment_client_edit_profile) {

    private lateinit var viewModel: ClientProfileViewModel

    private val binding by  viewBinding { FragmentClientEditProfileBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModel()
        setupViewModel()
        viewModel.currentUser()

    }

    private fun initViews() {
        binding.backEditprofil.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.llCalendar.setOnClickListener {
            setBirthday()
        }

        binding.btnSave.setOnClickListener {
            with(binding) {
                val fullName = etSurname.text.toString()

                val editClientRequest = EditClientRequest(
                    fullName = fullName
                )
                viewModel.editProfileClient(editClientRequest)
            }
        }
    }


    private fun observeViewModel(){

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.clientEditProfile.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showLoading()
                    }
                    is UiStateObject.SUCCESS -> {
                        dismissLoading()
                        requireActivity().onBackPressed()
                    }
                    is UiStateObject.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.currentUser.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                    }
                    is UiStateObject.SUCCESS -> {
                        val editProfile = it.data
                        Log.d("dasdasdasd@@@", "observeViewModel: ${it.data}")
                        binding.etSurname.text = Editable.Factory.getInstance().newEditable(editProfile.fullName)
                    }
                    is UiStateObject.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setBirthday() {
        val datePicker = Calendar.getInstance()
        val year = datePicker[Calendar.YEAR]
        val month = datePicker[Calendar.MONTH]
        val day = datePicker[Calendar.DAY_OF_MONTH]
        val date =
            DatePickerDialog.OnDateSetListener { picker, pickedYear, pickedMonth, pickedDay ->
                datePicker[Calendar.YEAR] = pickedYear
                datePicker[Calendar.MONTH] = pickedMonth
                datePicker[Calendar.DAY_OF_MONTH] = pickedDay
                val dateFormat = "dd.MM.yyyy"
                val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
                binding.tvBirthday.text = simpleDateFormat.format(datePicker.time)
            }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            date,
            year, month, day
        )
        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        datePickerDialog.show()

    }

    private fun setupViewModel() {
        val token = SharedPref(requireContext()).getString(Constants.KEY_ACCESS_TOKEN)
        viewModel = ViewModelProvider(
            this,
            ClientProfileViewModelFactory(
                ClientProfileRepository(
                    ApiClient.createServiceWithAuth(
                        ApiService::class.java,token!!))
            )
        )[ClientProfileViewModel::class.java]
    }


}