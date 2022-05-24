package com.heymaster.heymaster.adapters.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemHistoryBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class HistoryBookingAdapter(var list: ArrayList<UActiveBookingM>): RecyclerView.Adapter<HistoryBookingAdapter.HistoryBookingVH>() {

    inner class HistoryBookingVH(private val binding: ItemHistoryBookingBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userActiveBooking: UActiveBookingM) {
            binding.tvJob.text = userActiveBooking.jobType
            binding.imProfilePhoto.setImageResource(userActiveBooking.profileImg)
            binding.tvNameWorker.text = userActiveBooking.nameEmp
            binding.tvDateBooking.text = userActiveBooking.orderDate
            binding.tvPhoneNumber.text = userActiveBooking.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryBookingVH {
        return HistoryBookingVH(ItemHistoryBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HistoryBookingVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}