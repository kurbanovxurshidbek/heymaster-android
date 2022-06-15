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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.*
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.MasterRegisterRequest
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.adapter.DistrictsAdapter
import com.heymaster.heymaster.ui.adapter.ProfessionAdapter
import com.heymaster.heymaster.ui.adapter.RegionsAdapter
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthSharedViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
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
import kotlin.collections.ArrayList

class MasterSignUpFragment : Fragment(R.layout.fragment_master_sign_up) {

    private val binding by viewBinding { FragmentMasterSignUpBinding.bind(it) }
    private lateinit var sharedViewModel: AuthSharedViewModel

    private lateinit var viewModel: AuthViewModel

    private lateinit var dialogDistrict: Dialog
    private lateinit var dialogRegion: Dialog
    private lateinit var dialogProfession: Dialog

    private val regionsAdapter by lazy { RegionsAdapter() }
    private val districtsAdapter by lazy { DistrictsAdapter() }
    private val professionsAdapter by lazy { ProfessionAdapter() }

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


        observeViewModel()

        binding.etMasterGender.setOnClickListener {
            showChooseGenderDialog()
        }

        binding.etMasterBirthday.setOnClickListener {
            setBirthday()

        }

        binding.etMasterRegion.setOnClickListener {
            viewModel.getRegions()
            showRegionsDialog()
            binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable("")
        }

        regionsAdapter.itemClickListener = {
            dialogRegion.dismiss()
            binding.etMasterRegion.text = Editable.Factory.getInstance().newEditable(it.nameUz)
            viewModel.getDistrictsFromRegion(it.id)
            binding.etMasterDistrict.visibility = View.VISIBLE
        }

        binding.etMasterDistrict.setOnClickListener {
            showDistrictsDialog()
        }

        districtsAdapter.itemClickListener = {
            dialogDistrict.dismiss()
            binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable(it.nameUz)
        }

        binding.etMasterProfessions.setOnClickListener {
            viewModel.getProfessions()
            showProfessionsDialog()
        }

        professionsAdapter.itemClickListener = {
            dialogProfession.dismiss()
            binding.etMasterProfessions.text = Editable.Factory.getInstance().newEditable(it.name)
        }

        binding.etMasterExperience.setOnClickListener {
            showExperienceDialog()
        }

        sharedViewModel.masterSignUp.observe(viewLifecycleOwner) {
            with(binding) {
                if (etMasterFullname.text.isNotEmpty()
//                    && etMasterBirthday.text!!.isNotEmpty()
//                    && etMasterGender.text!!.isNotEmpty()
                ) {
                    val fullName = etMasterFullname.text.toString()
                    val gender = etMasterGender.text.toString() == MALE
                    val birthDay = etMasterBirthday.text.toString()
                    val regionId: Int = 3
                    val districtId:Int = 208
                    val password = SharedPref(requireContext()).getString(KEY_CONFIRM_CODE)
                    Log.d("@@@phone", "onViewCreated: $phoneNumber")
                    viewModel.masterRegister(MasterRegisterRequest(fullName = fullName, phoneNumber = phoneNumber,districtId = districtId,  regionId = regionId, deviceId = "dfdsfds", deviceLan = "edsfds", password = password, professionIdList = ArrayList(1), gender = gender))
                    Log.d("@@@@key", "onViewCreated: ${viewModel.masterRegister}")

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
            viewModel.regions.collect {
                when (it) {
                    is UiStateList.LOADING -> {

                    }
                    is UiStateList.SUCCESS -> {
                        regionsAdapter.submitList(it.data)
                    }
                    is UiStateList.ERROR -> {

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.districts.collect {
                when (it) {
                    is UiStateList.LOADING -> {

                    }
                    is UiStateList.SUCCESS -> {
                        districtsAdapter.submitList(it.data)
                    }
                    is UiStateList.ERROR -> {

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.professions.collect {
                when (it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        professionsAdapter.submitList(it.data.`object`!!)
                    }
                    is UiStateObject.ERROR -> {

                    }
                    else -> Unit
                }
            }
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

        binding1.tvMale.setOnClickListener {
            binding.etMasterGender.text = Editable.Factory.getInstance().newEditable(binding1.tvMale.text)
            dialog.dismiss()
        }

        binding1.tvFemale.setOnClickListener {
            binding.etMasterGender.text = Editable.Factory.getInstance().newEditable(binding1.tvFemale.text)
            dialog.dismiss()
        }


        dialog.show()

    }

    private fun showRegionsDialog() {
        dialogRegion = Dialog(requireContext())
        val binding2 = DialogForRegionsBinding.inflate(layoutInflater)
        dialogRegion.setContentView(binding2.root)
        dialogRegion.setCancelable(true)
        dialogRegion.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding2.rvRegions.adapter = regionsAdapter

        dialogRegion.show()

    }

    private fun showDistrictsDialog() {
        dialogDistrict = Dialog(requireContext())
        val binding2 = DialogForDistrictsBinding.inflate(layoutInflater)
        dialogDistrict.setContentView(binding2.root)
        dialogDistrict.setCancelable(true)
        dialogDistrict.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding2.rvDistrict.adapter = districtsAdapter

        dialogDistrict.show()

    }

    private fun showProfessionsDialog() {
        dialogProfession = Dialog(requireContext())
        val binding2 = DialogForProfessionsBinding.inflate(layoutInflater)
        dialogProfession.setContentView(binding2.root)
        dialogProfession.setCancelable(true)
        dialogProfession.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding2.rvProfessions.adapter = professionsAdapter

        dialogProfession.show()

    }

    private fun showExperienceDialog() {
        val dialogExperience = Dialog(requireContext())
        val binding2 = DialogExperienceBinding.inflate(layoutInflater)
        dialogExperience.setContentView(binding2.root)
        dialogExperience.setCancelable(true)
        dialogExperience.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding2.tv01.setOnClickListener {
            binding.etMasterExperience.text = Editable.Factory.getInstance().newEditable(binding2.tv01.text)
            dialogExperience.dismiss()
        }

        binding2.tv13.setOnClickListener {
            binding.etMasterExperience.text = Editable.Factory.getInstance().newEditable(binding2.tv13.text)
            dialogExperience.dismiss()
        }

        binding2.tv35.setOnClickListener {
            binding.etMasterExperience.text = Editable.Factory.getInstance().newEditable(binding2.tv35.text)
            dialogExperience.dismiss()
        }



        dialogExperience.show()

    }



    private fun setBirthday() {
        val datePicker = Calendar.getInstance()
        val year = datePicker[Calendar.YEAR]
        val month = datePicker[Calendar.MONTH]
        val day = datePicker[Calendar.DAY_OF_MONTH]
        val date =
            android.app.DatePickerDialog.OnDateSetListener { picker, pickedYear, pickedMonth, pickedDay ->
                datePicker[Calendar.YEAR] = pickedYear
                datePicker[Calendar.MONTH] = pickedMonth
                datePicker[Calendar.DAY_OF_MONTH] = pickedDay
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
