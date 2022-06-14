package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentLoginBinding
import com.heymaster.heymaster.model.auth.LoginRequest
import com.heymaster.heymaster.model.auth.LoginResponse
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_PHONE_NUMBER
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import java.lang.StringBuilder


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var validPhoneNumber: String
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        observeViewModel()

        editPhoneNumberListener()

        binding.btnContinue.setOnClickListener {
            if (binding.etPhoneNumber.text.toString().isNotEmpty()) {
                if (validPhoneNumber.length > 12) {
                SharedPref(requireContext()).saveString(KEY_PHONE_NUMBER, validPhoneNumber)
                viewModel.login(LoginRequest(validPhoneNumber))
            } else {
                Toast.makeText(requireContext(), "Invalid phone number", Toast.LENGTH_SHORT).show()
            }
        }

        }

    }

    private fun editPhoneNumberListener() {
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(number: Editable?) {
                if (number.toString().length == 12) {
                    hideKeyboard()
                    val phoneNumber = convertFormatPhoneNumber(number)
                    if (isValidPhoneNumber(phoneNumber)) {
                        validPhoneNumber = "+998$phoneNumber"
                    } else {
                        validPhoneNumber  = ""
                    }
                } else {
                    validPhoneNumber = ""
                }
            }
        })
    }



    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.login.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        SharedPref(requireContext()).saveString(KEY_CONFIRM_CODE, it.data.`object`.toString())
                        launchConfirmFragment()

                    }
                    is UiStateObject.ERROR -> {
                    }
                    else -> Unit
                }
            }
        }


    }

    private fun launchConfirmFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_verificationFragment)

    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(AuthRepository(ApiClient.createService(ApiService::class.java)))
        )[AuthViewModel::class.java]
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun convertFormatPhoneNumber(number: Editable?): String {
        val phoneNumber = StringBuilder()
        for (i in number.toString()) {
            if (i != ' ') {
                phoneNumber.append(i)
            }
        }
        return phoneNumber.toString()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val code = phoneNumber.substring(0,2)
        if (isBeeline(code)
            || isUms(code)
            || isUzMobile(code)
            || isPerfectum(code)
            || isHumans(code)
            || isUcell(code)
        ) {
            return true
        }
        return false
    }

    private fun isPerfectum(code: String): Boolean {
        if (code == "98") {
            return true
        }
        return false
    }

    private fun isUzMobile(code: String): Boolean {
        if (code == "99" || code == "95") {
            return true
        }
        return false
    }

    private fun isUms(code: String): Boolean {
        if (code == "97" || code == "88") {
            return true
        }
        return false
    }

    private fun isBeeline(code: String): Boolean {
        if (code == "90" || code == "91") {
            return true
        }
        return false
    }

    private fun isUcell(code: String): Boolean {
        if (code == "93" || code == "94") {
            return true
        }
        return false
    }

    private fun isHumans(code: String): Boolean {
        if (code == "33") {
            return true
        }
        return false
    }


}


