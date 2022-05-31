package com.heymaster.heymaster.role.master.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentMasterSearchBinding
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding

class MasterSearchFragment: BaseFragment(R.layout.fragment_master_search) {
    private val binding by viewBinding { FragmentMasterSearchBinding.bind(it) }

    private lateinit var imm: InputMethodManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        showKeyboard()
    }

    private fun showKeyboard() {
        binding.etHomeSearch.requestFocus()
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyBoard(){
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0)
    }
}