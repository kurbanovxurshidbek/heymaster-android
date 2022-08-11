package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.data.network.ApiClient
import com.heymaster.heymaster.data.network.ApiService
import com.heymaster.heymaster.databinding.FragmentConfirmBinding
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.ConfirmResponse
import com.heymaster.heymaster.model.auth.LoginRequest
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.auth.AuthRepository
import com.heymaster.heymaster.ui.auth.AuthViewModel
import com.heymaster.heymaster.ui.auth.AuthViewModelFactory
import com.heymaster.heymaster.utils.Constants.CLIENT
import com.heymaster.heymaster.utils.Constants.DEMO_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_ACCESS_TOKEN
import com.heymaster.heymaster.utils.Constants.KEY_CONFIRM_CODE
import com.heymaster.heymaster.utils.Constants.KEY_LOGIN_SAVED
import com.heymaster.heymaster.utils.Constants.KEY_PHONE_NUMBER
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.Constants.KEY_VERIFICATION_ID
import com.heymaster.heymaster.utils.Constants.MASTER
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.viewBinding
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit


class ConfirmFragment : Fragment(R.layout.fragment_confirm) {


    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val binding by viewBinding { FragmentConfirmBinding.bind(it) }
    private lateinit var viewModel: AuthViewModel

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var imm: InputMethodManager


    private var verificationId: String = ""


    private var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneNumber = SharedPref(requireContext()).getString(KEY_PHONE_NUMBER)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        setupViewModel()
        viewModel.startTimer()

        checkConfirmCode()
        setupUI()

        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Log.d("codee", "ssss")
                verificationId = p0
                SharedPref(requireContext()).saveString(KEY_VERIFICATION_ID, verificationId)

            }
        }


        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.confirm.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {
                        binding.progressHome.customProgress.visibility = View.VISIBLE
                    }
                    is UiStateObject.SUCCESS -> {
                        binding.progressHome.customProgress.visibility = View.GONE
                        launchSignUpOrHome(it.data)
                    }

                    is UiStateObject.ERROR -> {
                        Log.d("ERRORTAG", "observeViewModel: ${it.message}")
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.login.collect { it ->
                when (it) {
                    is UiStateObject.LOADING -> {

                    }
                    is UiStateObject.SUCCESS -> {
                        SharedPref(requireContext()).saveString(KEY_CONFIRM_CODE, it.data.`object`.toString())
//                        Toast.makeText(requireContext(), "${it.data.`object`}", Toast.LENGTH_SHORT).show()
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
            SharedPref(requireContext()).saveString(KEY_USER_ROLE, confirmResponse.message)
            if (confirmResponse.message == CLIENT) {
                callClientActivity()
            } else if (confirmResponse.message == MASTER){
                callMasterActivity()
            }

        } else {
            launchSignUpFragment()
        }
    }

    private fun launchSignUpFragment() {
        findNavController().navigate(R.id.action_verificationFragment_to_signUpFragment)
    }

    private fun setupUI() {
        viewModel.formattedGame.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
            if(it == "00:00") {
                binding.tvDidntResend.visibility = View.VISIBLE
                binding.tvResend.visibility = View.VISIBLE
                SharedPref(requireContext()).saveString(KEY_CONFIRM_CODE, "")
            } else {
                binding.tvDidntResend.visibility = View.GONE
                binding.tvResend.visibility = View.GONE
            }
        }

        binding.tvResend.setOnClickListener {
            viewModel.login(LoginRequest(phoneNumber!!))
            sendVerificationCode(phoneNumber!!, callback)
            viewModel.startTimer()
        }
    }

    private fun sendVerificationCode(
        number: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun checkConfirmCode() {
        binding.etVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(code: Editable?) {
                if (code.toString().length >= 6) {
                    if (code.toString() == DEMO_CONFIRM_CODE) {
                        val code = SharedPref(requireContext()).getString(KEY_CONFIRM_CODE)
                        viewModel.confirm(ConfirmRequest(code!!, phoneNumber!!))
                        hideKeyboard()
                    } else {
                        val code = SharedPref(requireContext()).getString(KEY_CONFIRM_CODE)
                        viewModel.confirm(ConfirmRequest(code!!, phoneNumber!!))
                        verifyCode(code.toString())
                        hideKeyboard()
                    }

                }

            }

        })
    }


    private fun verifyCode(code: String){
        val verificationId = SharedPref(requireContext()).getString(KEY_VERIFICATION_ID)
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Log.d("ConfirmFragment", "signInWithCredential: ")
                    val code = SharedPref(requireContext()).getString(KEY_CONFIRM_CODE)
                    viewModel.confirm(ConfirmRequest(code!!, phoneNumber!!))
                }
            }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(AuthRepository(ApiClient.createService(ApiService::class.java)))
        )[AuthViewModel::class.java]
    }

    private fun callClientActivity() {
        val activity = context as AppCompatActivity
        startActivity(Intent(requireContext(), ClientActivity::class.java))
        activity.finish()

    }

    private fun callMasterActivity() {
        val activity = context as AppCompatActivity
        startActivity(Intent(requireContext(), MasterActivity::class.java))
        activity.finish()
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun showKeyboard() {

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}


