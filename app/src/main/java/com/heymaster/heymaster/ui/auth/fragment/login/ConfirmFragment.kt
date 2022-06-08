package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentConfirmBinding
import com.heymaster.heymaster.model.User
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.ConfirmResponse
import com.heymaster.heymaster.model.auth.LoginResponse
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect


class ConfirmFragment : Fragment(R.layout.fragment_confirm) {

    private val binding by viewBinding { FragmentConfirmBinding.bind(it) }
    private lateinit var viewModel: AuthViewModel

    private lateinit var currentUser: User


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.startTimer()

        checkConfirmCode()
        setupUI()

        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.confirm.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {


                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                        Log.d("@@@", "observe: ${it.data.success}")
                        launchSignUpOrHome(it.data)
                    }

                    is UiStateObject.ERROR -> {
                        Log.d("ERRORTAG", "observeViewModel: ${it.message}")
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun launchSignUpOrHome(confirmResponse: ConfirmResponse) {
        if (confirmResponse.success) {
            SharedPref(requireContext()).saveString(KEY_ACCESS_TOKEN, confirmResponse.`object`.toString())
            SharedPref(requireContext()).saveBoolean(KEY_LOGIN_SAVED, true)
            if (confirmResponse.message == CLIENT) {
                callClientActivity()
            } else if (confirmResponse.message == MASTER){
                callMasterActivity()
            }

        } else {
            findNavController().navigate(R.id.action_verificationFragment_to_signUpFragment)
        }
    }

    private fun setupUI() {
        viewModel.formattedGame.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
            if(it == "00:00") {
                binding.tvDidntResend.visibility = View.VISIBLE
                binding.tvResend.visibility = View.VISIBLE
            } else {
                binding.tvDidntResend.visibility = View.GONE
                binding.tvResend.visibility = View.GONE
            }
        }

        binding.tvResend.setOnClickListener {
            viewModel.startTimer()
        }
    }

    private fun checkConfirmCode() {
        binding.etVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(code: Editable?) {
                val phoneNumber = arguments?.let {
                    it.getString("phoneNumber")
                }
                if (code.toString().length >= 4) {
                    viewModel.confirm(ConfirmRequest(code.toString(), phoneNumber!!))
                }

            }

        })
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(AuthRepository(ApiClient.createService(ApiService::class.java)))
        )[AuthViewModel::class.java]
    }

    private fun callClientActivity() {
        startActivity(Intent(requireContext(), ClientActivity::class.java))
    }

    private fun callMasterActivity() {
        startActivity(Intent(requireContext(), MasterActivity::class.java))
    }

    companion object {
        const val CLIENT = "CLIENT"
        const val MASTER = "MASTER"
    }
}


