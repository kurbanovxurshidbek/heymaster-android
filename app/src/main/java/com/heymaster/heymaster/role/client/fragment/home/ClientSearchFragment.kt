package com.heymaster.heymaster.role.client.fragment.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.FragmentUserSearchBinding
import com.heymaster.heymaster.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class ClientSearchFragment: BaseFragment(R.layout.fragment_user_search) {

    private val binding by viewBinding { FragmentUserSearchBinding.bind(it) }

    private lateinit var imm: InputMethodManager




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        showKeyboard()
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    binding.lottieSearch.visibility = View.GONE
                } else {
                    binding.lottieSearch.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun showKeyboard() {
        binding.etSearch.requestFocus()
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyBoard(){
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0)
    }


}