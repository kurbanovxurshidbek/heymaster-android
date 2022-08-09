package com.heymaster.heymaster.role.master.fragment.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.heymaster.heymaster.App
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.databinding.DialogChooseLanguageBinding
import com.heymaster.heymaster.databinding.DialogLogOutBinding
import com.heymaster.heymaster.databinding.FragmentMasterChildProfileBinding
import com.heymaster.heymaster.manager.LocaleManager
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.utils.Constants
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterChildProfileFragment : Fragment(R.layout.fragment_master_child_profile) {
    private val binding by viewBinding { FragmentMasterChildProfileBinding.bind(it) }
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        binding.apply {
            btnUserNotification.setOnClickListener {
                findNavController().navigate(R.id.notificationFragment)
            }
            btnUserChangeLanguage.setOnClickListener {
                showChooseLanguageDialog()
            }
            btnUserAboutUs.setOnClickListener {
                findNavController().navigate(R.id.masterProfileAboutFragment)
            }

            btnMasterHelp.setOnClickListener {
                findNavController().navigate(R.id.masterProfileHelpFragment)
            }
            btnUserLogOut.setOnClickListener {
                showLogOutDialog()
            }

        }
    }

    private fun showLogOutDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogLogOutBinding =
            DialogLogOutBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOk.setOnClickListener {
            SharedPref(requireContext()).removeString(Constants.KEY_ACCESS_TOKEN)
            SharedPref(requireContext()).removeString(Constants.KEY_PHONE_NUMBER)
            SharedPref(requireContext()).removeString(Constants.KEY_LOGIN_SAVED)
            SharedPref(requireContext()).removeString(Constants.KEY_CONFIRM_CODE)
            SharedPref(requireContext()).removeString(Constants.KEY_VERIFICATION_ID)
            SharedPref(requireContext()).removeString(Constants.KEY_DEVICE_TOKEN)
            SharedPref(requireContext()).removeString(Constants.KEY_INTRO_SAVED)
            SharedPref(requireContext()).removeString(Constants.DEMO_PHONE_NUMBER)
            SharedPref(requireContext()).removeString(Constants.DEMO_CONFIRM_CODE)
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showChooseLanguageDialog() {
        val dialog = Dialog(requireContext())
        val binding: DialogChooseLanguageBinding =
            DialogChooseLanguageBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnUzbek.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_UZBEK)
            LocaleManager(context).setLocale(requireContext())

        }
        binding.btnRussian.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_RUSSIAN)
        }
        binding.btnEnglish.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_ENGLISH)
        }

        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.masterProfileFragment)
            dialog.dismiss()

        }
        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

}