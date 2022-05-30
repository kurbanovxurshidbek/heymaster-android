package com.heymaster.heymaster.adapters.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.booking.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class MasterActiveBookingAdapter(private val list: ArrayList<UActiveBookingM>): ListAdapter<UActiveBookingM, MasterActiveBookingVH>(ItemMasterActiveBookingDiffCallBack()) {

    inner class MasterActiveBookingVH(private val binding: ItemActiveBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
         fun onBind(uActiveBookingM: UActiveBookingM) {
             binding.tvJob.text = uActiveBookingM.jobType
             binding.imProfilePhoto.setImageResource(uActiveBookingM.profileImg)
             binding.tvNameWorker.text = uActiveBookingM.nameEmp
             binding.tvDateBooking.text = uActiveBookingM.orderDate
             binding.tvPhoneNumber.text = uActiveBookingM.phoneNumber
         }
    }

    private class ItemMasterActiveBookingDiffCallBack: DiffUtil.ItemCallback<UActiveBookingM>() {
        override fun areItemsTheSame(oldItem: UActiveBookingM, newItem: UActiveBookingM): Boolean {
            return oldItem.idEmp == newItem.idEmp
        }

        override fun areContentsTheSame(oldItem: UActiveBookingM,newItem: UActiveBookingM,): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterActiveBookingVH {
        return MasterActiveBookingVH(ItemActiveBookingMasterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MasterActiveBookingVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}