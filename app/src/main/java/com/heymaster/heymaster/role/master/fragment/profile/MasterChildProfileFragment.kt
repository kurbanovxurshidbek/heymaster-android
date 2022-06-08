package com.heymaster.heymaster.role.master.fragment.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heymaster.heymaster.App
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.DialogChooseLanguageBinding
import com.heymaster.heymaster.databinding.DialogLogOutBinding
import com.heymaster.heymaster.databinding.FragmentMasterChildProfileBinding
import com.heymaster.heymaster.manager.LocaleManager
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterChildProfileFragment : Fragment(R.layout.fragment_master_child_profile) {
    private val binding by viewBinding { FragmentMasterChildProfileBinding.bind(it) }

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

        }
        binding.btnRussian.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_RUSSIAN)

        }
        binding.btnEnglish.setOnClickListener {
            App.localeManager!!.setNewLocale(requireContext(), LocaleManager.LANGUAGE_ENGLISH)
        }

        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.tvCancel.setOnClickListener {
            dialog.dismiss()

        }

        dialog.show()

    }

}