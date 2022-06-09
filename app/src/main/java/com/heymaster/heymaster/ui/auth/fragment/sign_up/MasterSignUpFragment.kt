package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterSignUpBinding
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterSignUpFragment : Fragment(R.layout.fragment_master_sign_up) {

    private val binding by viewBinding { FragmentMasterSignUpBinding.bind(it) }


    private var spEmptyItem: SmartMaterialSpinner<String>? = null
    private var provinceList: MutableList<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()


    }

    private fun setupSpinner() {
        provinceList = ArrayList()

        provinceList?.add("Kampong Thom")
        provinceList?.add("Kampong Cham")
        provinceList?.add("Kampong Chhnang")
        provinceList?.add("Phnom Penh")
        provinceList?.add("Kandal")
        provinceList?.add("Kampot")
        provinceList?.add("Kampong Thom")
        provinceList?.add("Kampong Cham")
        provinceList?.add("Kampong Chhnang")
        provinceList?.add("Phnom Penh")
        provinceList?.add("Kandal")

        binding.spLocation.item = provinceList as List<Any>?

        binding.spLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(requireContext(), provinceList!![position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }


    }
