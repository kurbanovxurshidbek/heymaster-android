package com.heymaster.heymaster.role.client.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.UserBookingBottomSheetBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class RateBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: UserBookingBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = UserBookingBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = getItemFromBundle()



    }

    private fun getItemFromBundle(): UActiveBookingM? {
       return arguments?.getParcelable<UActiveBookingM>("item")
    }

    companion object {
        fun newInstance(item: UActiveBookingM): RateBottomSheetFragment {
            val fragment = RateBottomSheetFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putParcelable("item", item)
                }
            }
            return fragment
        }
    }


}