package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentLoginBinding
import com.heymaster.heymaster.utils.extensions.viewBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var isPhoneNumberValid = false
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length == 12) {
                    hideKeyboard()
                    isPhoneNumberValid = true
                }
            }
        })

        binding.btnContinue.setOnClickListener {
            if (isPhoneNumberValid) {
                findNavController().navigate(R.id.action_loginFragment_to_verificationFragment)
            } else {
                Toast.makeText(requireContext(), "Number invalid", Toast.LENGTH_SHORT).show()
            }


        }
     }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}