package com.heymaster.heymaster.role.master.fragment.profile

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.*
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.master.repository.MasterProfileRepository
import com.heymaster.heymaster.role.master.viewmodel.MasterProfileViewModel
import com.heymaster.heymaster.role.master.viewmodel.factory.MasterProfileViewModelFactory
import com.heymaster.heymaster.ui.adapter.DistrictsAdapter
import com.heymaster.heymaster.ui.adapter.ProfessionAdapter
import com.heymaster.heymaster.ui.adapter.RegionsAdapter
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*


class MasterEditProfileFragment : BaseFragment(R.layout.fragment_master_edit_profil) {

    private lateinit var viewModel:MasterProfileViewModel

    private val binding by viewBinding { FragmentMasterEditProfilBinding.bind(it) }
    private var gender: String? = null

    private lateinit var dialogDistrict: Dialog
    private lateinit var dialogRegion: Dialog
    private lateinit var dialogProfession: Dialog

    private var regionId: Int? = null
    private var districtId: Int? = null
    private var professionsList = ArrayList<Int>()
    private var experienceYear: Int? = null

    private val regionsAdapter by lazy { RegionsAdapter() }
    private val districtsAdapter by lazy { DistrictsAdapter() }
    private val professionsAdapter by lazy { ProfessionAdapter() }

    private var phoneNumber: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initViews()
        observeViewModel()
        viewModel.getMasterProfile()


    }

    private fun initViews() {
        manageGender()

        binding.backEditprofil.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.llCalendar.setOnClickListener {
            setBirthday()
        }

        binding.etMasterRegion.setOnClickListener {
            showRegionsDialog()
            binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable("")
            viewModel.getRegions()
        }

        regionsAdapter.itemClickListener = {
            dialogRegion.dismiss()
            binding.etMasterRegion.text = Editable.Factory.getInstance().newEditable(it.nameUz)
            regionId = it.id
            binding.etMasterDistrict.visibility = View.VISIBLE
        }

        binding.etMasterDistrict.setOnClickListener {
            showDistrictsDialog()
            viewModel.getDistrictsFromRegion(regionId!!)
        }

        districtsAdapter.itemClickListener = {
            dialogDistrict.dismiss()
            binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable(it.nameUz)
            districtId = it.id
        }

        binding.etMasterProfessions.setOnClickListener {
            showProfessionsDialog()
            viewModel.getProfessions()
        }

        professionsAdapter.itemClickListener = {
            dialogProfession.dismiss()
            binding.etMasterProfessions.text = Editable.Factory.getInstance().newEditable(it.name)
            professionsList.add(it.id)
        }

        binding.etMasterExperience.setOnClickListener {
            showExperienceDialog()
        }



    }

    private fun observeViewModel(){

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.regions.collect {
                when (it) {
                    is UiStateList.LOADING -> {
                        dialogRegion.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        dialogRegion.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.GONE
                        regionsAdapter.submitList(it.data)
                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.masterProfile.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                    }
                    is UiStateObject.SUCCESS -> {
                        val editProfile = it.data
                        Log.d("dasdasdasd@@@", "observeViewModel: ${it.data}")
                        binding.etSurname.text = Editable.Factory.getInstance().newEditable(editProfile.fullName)
                        binding.etMasterRegion.text = Editable.Factory.getInstance().newEditable(editProfile.location.region.nameUz)
                        binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable(editProfile.location.district.nameUz)

                        editProfile.professionList.forEach {
                            binding.etMasterProfessions.text = Editable.Factory.getInstance().newEditable(it.toString())
                        }

                        binding.etMasterExperience.text = Editable.Factory.getInstance().newEditable(editProfile.experienceYear.toString())
                        binding.etPhoneNumber.text = Editable.Factory.getInstance().newEditable(editProfile.phoneNumber)
                    }
                    is UiStateObject.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.districts.collect {
                when (it) {
                    is UiStateList.LOADING -> {
                        dialogDistrict.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.VISIBLE
                    }
                    is UiStateList.SUCCESS -> {
                        dialogDistrict.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.GONE

                        districtsAdapter.submitList(it.data)
                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.professions.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        dialogProfession.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        dialogProfession.findViewById<ProgressBar>(R.id.progress_dialog).visibility = View.GONE
                        professionsAdapter.submitList(it.data.`object`!!)
                    }
                    is UiStateObject.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

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
            binding.etMasterExperience.text =
                Editable.Factory.getInstance().newEditable(binding2.tv01.text)
            experienceYear = 1
            dialogExperience.dismiss()
        }

        binding2.tv13.setOnClickListener {
            binding.etMasterExperience.text =
                Editable.Factory.getInstance().newEditable(binding2.tv13.text)
            experienceYear = 3
            dialogExperience.dismiss()
        }

        binding2.tv35.setOnClickListener {
            binding.etMasterExperience.text =
                Editable.Factory.getInstance().newEditable(binding2.tv35.text)
            experienceYear = 5
            dialogExperience.dismiss()
        }
        dialogExperience.show()

    }

    private fun manageGender() {
        binding.checkboxMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkboxFemale.isChecked = false
                gender = getString(R.string.str_gender_male)
            }
        }
        binding.checkboxFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkboxMale.isChecked = false
                gender = getString(R.string.str_gender_female)
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
            MasterProfileViewModelFactory(
                MasterProfileRepository(
                    ApiClient.createServiceWithAuth(
                        ApiService::class.java,token!!))
            )
        )[MasterProfileViewModel::class.java]
    }



}