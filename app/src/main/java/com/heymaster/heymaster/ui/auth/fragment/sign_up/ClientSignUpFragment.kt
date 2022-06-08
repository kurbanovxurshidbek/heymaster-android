package com.heymaster.heymaster.ui.auth.fragment.sign_up

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.DialogChooseGenderBinding
import com.heymaster.heymaster.databinding.FragmentUserSignUpBinding
import com.heymaster.heymaster.ui.auth.AuthSharedViewModel
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.utils.extensions.viewBinding


class ClientSignUpFragment : BaseFragment(R.layout.fragment_user_sign_up) {

    private val binding by viewBinding { FragmentUserSignUpBinding.bind(it) }
    private lateinit var viewModel: AuthSharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        binding.etUserGender.setOnClickListener {
            showChooseGenderDialog()
        }

        viewModel.click.observe(viewLifecycleOwner){
            startActivity(Intent(requireContext(), ClientActivity::class.java))
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
        viewModel = ViewModelProvider(requireActivity())[AuthSharedViewModel::class.java]
    }

    companion object {
        private const val MALE = "Male"
        private const val FEMALE = "Female"
    }

}