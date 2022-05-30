package com.heymaster.heymaster.ui.user.booking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.ActiveBookingAdapter
import com.heymaster.heymaster.databinding.FragmentUserActiveBookingBinding
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment
import com.heymaster.heymaster.utils.extensions.viewBinding


class UserActiveBookingFragment : BaseFragment(R.layout.fragment_user_active_booking) {

    private val binding by viewBinding { FragmentUserActiveBookingBinding.bind(it) }
    private lateinit var viewModel: UserBookingViewModel
    private val userActiveBookingAdapter by lazy { UserActiveBookingAdapter() }

    private lateinit var activeBookingAdapter: ActiveBookingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setupViewModel()
        viewModel.getBookings()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.bookings.collect { it ->
                when (it) {
                    is UiStateList.LOADING -> {
                        binding.progressUserActiveBooking.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressUserActiveBooking.customProgress.visibility = View.GONE
                        userActiveBookingAdapter.submitList(it.data)

                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRv() {
        val uActiveBookingM = UActiveBookingM(
            1,
            "Electric",
            R.drawable.intro_image_3,
            "Donald Trump",
            "01.01.2021",
            "+998 99 046 6901"
        )
        val list = ArrayList<UActiveBookingM>()
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)
        list.add(uActiveBookingM)


        activeBookingAdapter = ActiveBookingAdapter(list, requireContext())
        binding.rvUserActiveBookings.setLayoutManager(GridLayoutManager(context, 1))
        binding.rvUserActiveBookings.adapter = activeBookingAdapter
        activeBookingAdapter.setItemClickListener(ActiveBookingAdapter.ItemClickListener {
            showBottomSheet("rate_bottom_sheet_fragment",it)
        })

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserBookingViewModelFactory(UserBookingRepository(ApiClient.createService(ApiService::class.java)))
        )[UserBookingViewModel::class.java]
    }
}