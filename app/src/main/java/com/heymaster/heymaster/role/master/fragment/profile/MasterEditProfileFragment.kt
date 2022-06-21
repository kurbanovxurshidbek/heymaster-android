package com.heymaster.heymaster.role.master.fragment.profile

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterEditProfilBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.ui.adapter.DistrictsAdapter
import com.heymaster.heymaster.ui.adapter.ProfessionAdapter
import com.heymaster.heymaster.ui.adapter.RegionsAdapter
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.utils.extensions.viewBinding
import java.text.SimpleDateFormat
import java.util.*


class MasterEditProfileFragment : BaseFragment(R.layout.fragment_master_edit_profil) {

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
        initViews()

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

        }

        regionsAdapter.itemClickListener = {
            dialogRegion.dismiss()
            binding.etMasterRegion.text = Editable.Factory.getInstance().newEditable(it.nameUz)
            regionId = it.id
            binding.etMasterDistrict.visibility = View.VISIBLE
        }

        binding.etMasterDistrict.setOnClickListener {

        }

        districtsAdapter.itemClickListener = {
            dialogDistrict.dismiss()
            binding.etMasterDistrict.text = Editable.Factory.getInstance().newEditable(it.nameUz)
            districtId = it.id
        }

        binding.etMasterProfessions.setOnClickListener {

        }

        professionsAdapter.itemClickListener = {
            dialogProfession.dismiss()
            binding.etMasterProfessions.text = Editable.Factory.getInstance().newEditable(it.name)
            professionsList.add(it.id)
        }

        binding.etMasterExperience.setOnClickListener {

        }



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

}