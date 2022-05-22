package com.heymaster.heymaster.ui.auth.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentLoginBinding
import com.heymaster.heymaster.utils.extensions.enterPhoneNumber
import com.heymaster.heymaster.utils.extensions.viewBinding



class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding { FragmentLoginBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.enterPhoneNumber()

        binding.btnContinue.setOnClickListener {
            val number = binding.etPhoneNumber.text.toString()
            if (number.length >= 9) {
                findNavController().navigate(R.id.action_loginFragment_to_verificationFragment)
            } else {
                Toast.makeText(requireContext(), "No'mer xato", Toast.LENGTH_SHORT).show()
            }
        }
     }
}