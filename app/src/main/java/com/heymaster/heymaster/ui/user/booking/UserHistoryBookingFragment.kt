package com.heymaster.heymaster.ui.user.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.HistoryBookingAdapter
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment


class UserHistoryBookingFragment : BaseFragment(R.layout.fragment_user_history_booking) {

    private val binding by viewBinding { FragmentUserHistoryBookingBinding.bind(it) }
    private lateinit var viewModel: UserBookingViewModel
    private val adapter by lazy { UserHistoryBookingAdapter() }

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
                        binding.progressUserHistoryBooking.customProgress.visibility = View.VISIBLE

                    }
                    is UiStateList.SUCCESS -> {
                        binding.progressUserHistoryBooking.customProgress.visibility = View.GONE
                        adapter.submitList(it.data)

                    }
                    is UiStateList.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRv() {
        binding.rvUserHistoryBookings.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserBookingViewModelFactory(UserBookingRepository(ApiClient.createService(ApiService::class.java)))
        )[UserBookingViewModel::class.java]
    }


}