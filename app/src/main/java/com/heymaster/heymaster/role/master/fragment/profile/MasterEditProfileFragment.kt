package com.heymaster.heymaster.role.master.fragment.profile

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterEditProfilBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding
import java.text.SimpleDateFormat
import java.util.*


class MasterEditProfileFragment : BaseFragment(R.layout.fragment_master_edit_profil) {

    private val binding by viewBinding { FragmentMasterEditProfilBinding.bind(it) }
    private var gender: String? = null

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