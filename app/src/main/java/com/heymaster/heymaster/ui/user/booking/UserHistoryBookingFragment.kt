package com.heymaster.heymaster.ui.user.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.adapters.booking.HistoryBookingAdapter
import com.heymaster.heymaster.model.user_booking.UActiveBookingM
import com.heymaster.heymaster.ui.global.BaseFragment


class UserHistoryBookingFragment : BaseFragment(R.layout.fragment_user_history_booking) {

    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<UActiveBookingM>
    lateinit var adapter: HistoryBookingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_history_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.rv_user_history_bookings)
        recyclerView.setLayoutManager(GridLayoutManager(context,1))
        list = ArrayList()
        var activeBookingM = UActiveBookingM(1,"Electric",R.drawable.intro_image_1,"John Nottingham","12.01.2022","+998 99 046 6901")
        list.add(activeBookingM)
        list.add(activeBookingM)
        list.add(activeBookingM)
        list.add(activeBookingM)

        refreshAdapter(list)
    }

    private fun refreshAdapter(list: ArrayList<UActiveBookingM>) {
        adapter = HistoryBookingAdapter(list)
        recyclerView.adapter = adapter
    }

}