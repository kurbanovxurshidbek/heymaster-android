package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentVerificationBinding
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.ui.user.UserActivity
import com.heymaster.heymaster.ui.user.home.UserHomeRepository
import com.heymaster.heymaster.ui.user.home.UserHomeViewModel
import com.heymaster.heymaster.ui.user.home.UserHomeViewModelFactory
import com.heymaster.heymaster.utils.extensions.viewBinding


class ConfirmFragment : Fragment(R.layout.fragment_verification) {

    private val binding by viewBinding { FragmentVerificationBinding.bind(it) }
    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.startTimer()

        checkConfirmCode()
        setupUI()


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

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 4) {
                    findNavController().navigate(R.id.action_verificationFragment_to_signUpFragment)
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
}


