package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService

import com.heymaster.heymaster.databinding.DialogChooseGenderBinding
import com.heymaster.heymaster.databinding.FragmentUserSignUpBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.model.auth.ClientRegisterRequest
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthSharedViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants.CLIENT
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_PHONE_NUMBER
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class ClientSignUpFragment : BaseFragment(R.layout.fragment_user_sign_up) {

    private val TAG = ClientSignUpFragment::class.java.simpleName

    private val binding by viewBinding { FragmentUserSignUpBinding.bind(it) }
    private lateinit var sharedViewModel: AuthSharedViewModel
    private lateinit var viewModel: AuthViewModel

    private var phoneNumber: String? = null
    private var confirmCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneNumber = SharedPref(requireContext()).getString(KEY_PHONE_NUMBER)
        confirmCode = SharedPref(requireContext()).getString(KEY_CONFIRM_CODE)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViewModel()
        
        observeViewModel()

        binding.etUserGender.setOnClickListener {
            showChooseGenderDialog()
        }

        binding.etUserBirthday.setOnClickListener {
            setBirthday()

        }

        sharedViewModel.click.observe(viewLifecycleOwner){
            if (binding.etUserFullName.text.isNotEmpty()
                && binding.etUserGender.text.isNotEmpty()
                && binding.etUserBirthday.text!!.isNotEmpty()) {

                with(binding) {
                    val fullName = etUserFullName.text.toString()
                    val gender = etUserGender.text.toString() == MALE
                    val birthDay = etUserBirthday.text.toString()
                    val clientRegisterRequest = ClientRegisterRequest(birthDay, fullName, gender, phoneNumber)
                    viewModel.clientRegister(clientRegisterRequest)
                    
                }
            }

        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.clientRegister.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        
                    }
                    is UiStateObject.SUCCESS -> {
                        if (it.data.success) {
                            viewModel.confirm(ConfirmRequest(confirmCode!!, phoneNumber!!))
                        }

                    }
                    is UiStateObject.ERROR -> {
                        Log.d(TAG, "observeViewModel: ${it.message}")
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.confirm.collect { it ->
                    when (it) {
                        is UiStateObject.LOADING -> {
                            binding.progressHome.customProgress.visibility = View.VISIBLE

                        }
                        is UiStateObject.SUCCESS -> {
                            binding.progressHome.customProgress.visibility = View.GONE
                            if (it.data.success) {
                                SharedPref(requireContext()).saveString(KEY_ACCESS_TOKEN, it.data.`object`.toString())
                                SharedPref(requireContext()).saveString(KEY_USER_ROLE, CLIENT)
                                SharedPref(requireContext()).saveBoolean(KEY_LOGIN_SAVED, true)
                                SharedPref(requireContext()).saveString(KEY_ACCESS_TOKEN, it.data.`object`.toString())
                                Log.d("@@@Token", "observeToken: ${it.data.`object`.toString()}")
                                startActivity(Intent(requireContext(), ClientActivity::class.java))
                            }
                        }

                        is UiStateObject.ERROR -> {
                            Log.d(TAG, "observeViewModel: ${it.message}")
                        }
                        else -> Unit
                    }
                }

        }
    }



    private fun showChooseGenderDialog() {
        val dialog = Dialog(requireContext())
        val binding1 = DialogChooseGenderBinding.inflate(layoutInflater)
        dialog.setContentView(binding1.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (binding.etUserGender.text.toString() == MALE) {
            binding1.btnMale.isChecked = true
        } else if (binding.etUserGender.text.toString() == FEMALE){
            binding1.btnFemale.isChecked = true
        }

        binding1.tvSave.setOnClickListener {
            if (binding1.genderGroup.checkedRadioButtonId == binding1.btnMale.id) {
                binding.etUserGender.text = Editable.Factory.getInstance().newEditable(MALE)
                dialog.dismiss()
            } else {
                binding.etUserGender.text = Editable.Factory.getInstance().newEditable(FEMALE)
                dialog.dismiss()
            }
        }
        binding1.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    private fun setupViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity())[AuthSharedViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(AuthRepository(ApiClient.createService(ApiService::class.java)))
        )[AuthViewModel::class.java]
    }

    private fun setBirthday() {
        val datePicker = java.util.Calendar.getInstance()
        val year = datePicker[java.util.Calendar.YEAR]
        val month = datePicker[java.util.Calendar.MONTH]
        val day = datePicker[java.util.Calendar.DAY_OF_MONTH]
        val date =
            android.app.DatePickerDialog.OnDateSetListener { picker, pickedYear, pickedMonth, pickedDay ->
                datePicker[java.util.Calendar.YEAR] = pickedYear
                datePicker[java.util.Calendar.MONTH] = pickedMonth
                datePicker[java.util.Calendar.DAY_OF_MONTH] = pickedDay
                val dateFormat = "dd.MM.yyyy"
                val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
                binding.etUserBirthday.text = Editable.Factory.getInstance().newEditable(simpleDateFormat.format(datePicker.time))
            }

        val datePickerDialog = android.app.DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            date,
            year, month, day
        )
        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        datePickerDialog.show()

    }


    companion object {
        private const val MALE = "Male"
        private const val FEMALE = "Female"
    }

}