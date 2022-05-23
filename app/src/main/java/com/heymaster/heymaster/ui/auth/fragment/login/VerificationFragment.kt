package com.heymaster.heymaster.ui.auth.fragment.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentVerificationBinding
import com.heymaster.heymaster.ui.user.UserActivity
import com.heymaster.heymaster.utils.extensions.viewBinding


class VerificationFragment : Fragment(R.layout.fragment_verification) {

    private val binding by viewBinding { FragmentVerificationBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_verificationFragment_to_signUpFragment)
//            val code = binding.etVerificationCode.text.toString()
//            if (code.length >= 4){
////                startActivity(Intent(requireActivity(), UserActivity::class.java))
////                activity?.finish()
//
//            } else {
//                Toast.makeText(requireContext(), " Kod Kiriting", Toast.LENGTH_SHORT).show()
//            }

        }
    }


}