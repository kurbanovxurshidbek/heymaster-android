package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.DialogChooseGenderBinding
import com.heymaster.heymaster.databinding.FragmentMasterSignUpBinding
import com.heymaster.heymaster.model.District
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.MasterRegisterRequest
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthSharedViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.Constants.FEMALE
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.Constants.MALE
import com.heymaster.heymaster.utils.Constants.MASTER
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

class MasterSignUpFragment : Fragment(R.layout.fragment_master_sign_up) {

    private val binding by viewBinding { FragmentMasterSignUpBinding.bind(it) }
    private lateinit var sharedViewModel: AuthSharedViewModel

    private lateinit var viewModel: AuthViewModel

    private var phoneNumber: String? = null
    private var confirmCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneNumber = SharedPref(requireContext()).getString(Constants.KEY_PHONE_NUMBER)
        confirmCode = SharedPref(requireContext()).getString(Constants.KEY_CONFIRM_CODE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.getDistricts()

        observeViewModel()

        binding.etMasterGender.setOnClickListener {
            showChooseGenderDialog()
        }

        binding.etMasterBirthday.setOnClickListener {
            setBirthday()

        }

        sharedViewModel.masterSignUp.observe(viewLifecycleOwner) {
            with(binding) {
                if (etMaterFullName.text.isNotEmpty()
                    && etMasterBirthday.text!!.isNotEmpty()
                    && etMasterGender.text!!.isNotEmpty()
                ) {
                    val fullName = etMaterFullName.text.toString()
                    val gender = etMasterGender.text.toString() == MALE
                    val birthDay = etMasterBirthday.text.toString()
                    Log.d("@@@phone", "onViewCreated: $phoneNumber")
                    viewModel.masterRegister(MasterRegisterRequest(fullName = fullName, phoneNumber = phoneNumber, districtId = 208, regionId = 3))

                } else {
                    Toast.makeText(requireContext(), "Please fill the field first", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterRegister.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        if (it.data.success) {
                            viewModel.confirm(ConfirmRequest(confirmCode!!, phoneNumber!!))
                        }

                    }
                    is UiStateObject.ERROR -> {
                        Log.d("@@@error", "observeViewModel: ${it.message}")
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.confirm.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        if (it.data.success) {
                            SharedPref(requireContext()).saveString(KEY_ACCESS_TOKEN, it.data.`object`.toString())
                            SharedPref(requireContext()).saveString(KEY_USER_ROLE, MASTER)
                            SharedPref(requireContext()).saveBoolean(KEY_LOGIN_SAVED, true)
                            Log.d("@@@Token", "observeToken: ${it.data.`object`.toString()}")
                            startActivity(Intent(requireContext(), MasterActivity::class.java))
                            activity?.finish()
                        }
                    }

                    is UiStateObject.ERROR -> {
                        Log.d("TAG", "observeViewModel: ${it.message}")
                    }
                    else -> Unit
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.district.collect {
                when (it) {
                    is UiStateList.LOADING -> {

                    }
                    is UiStateList.SUCCESS -> {
                        Log.d("@@@", "observeViewModel: ${it.data}")
                        val list = mutableListOf<District>()
                        it.data?.forEach {
                            list.add(it)
                        }
                        setupSpinner(list)

                    }
                    is UiStateList.ERROR -> {

                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupSpinner(list: MutableList<District>) {
        Log.d("@@@", "setupSpinner: $list")

        binding.spLocation.item = list.reversed() as List<Any>?


        binding.spLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    private fun setupViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity())[AuthSharedViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(AuthRepository(ApiClient.createService(ApiService::class.java)))
        )[AuthViewModel::class.java]
    }

    private fun showChooseGenderDialog() {
        val dialog = Dialog(requireContext())
        val binding1 = DialogChooseGenderBinding.inflate(layoutInflater)
        dialog.setContentView(binding1.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (binding.etMasterGender.text.toString() == MALE) {
            binding1.btnMale.isChecked = true
        } else if (binding.etMasterGender.text.toString() == FEMALE){
            binding1.btnFemale.isChecked = true
        }

        binding1.tvSave.setOnClickListener {
            if (binding1.genderGroup.checkedRadioButtonId == binding1.btnMale.id) {
                binding.etMasterGender.text = Editable.Factory.getInstance().newEditable(MALE)
                dialog.dismiss()
            } else {
                binding.etMasterGender.text = Editable.Factory.getInstance().newEditable(FEMALE)
                dialog.dismiss()
            }
        }
        binding1.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

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
                binding.etMasterBirthday.text = Editable.Factory.getInstance().newEditable(simpleDateFormat.format(datePicker.time))
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


    }
