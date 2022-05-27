package com.heymaster.heymaster.adapters.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.booking.MasterHistoryBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemHistoryBookingMasterBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class MasterHistoryBookingAdapter(private var list: ArrayList<UActiveBookingM>): ListAdapter<UActiveBookingM, MasterHistoryBookingVH>(ItemMasterHistoryBookingDiffCallBack()) {

    inner class MasterHistoryBookingVH(private val binding: ItemHistoryBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun onBind(uActiveBookingM: UActiveBookingM) {
            binding.imProfilePhoto.setImageResource(uActiveBookingM.profileImg)
            binding.tvNameWorker.text = uActiveBookingM.nameEmp
            binding.tvDateBooking.text = uActiveBookingM.orderDate
            binding.tvPhoneNumber.text = uActiveBookingM.phoneNumber
        }
    }

    class ItemMasterHistoryBookingDiffCallBack: DiffUtil.ItemCallback<UActiveBookingM>() {
        override fun areItemsTheSame(oldItem: UActiveBookingM, newItem: UActiveBookingM): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: UActiveBookingM,
            newItem: UActiveBookingM,
        ): Boolean {
            return oldItem.idEmp == newItem.idEmp
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterHistoryBookingVH {
        return MasterHistoryBookingVH(ItemHistoryBookingMasterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MasterHistoryBookingVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}