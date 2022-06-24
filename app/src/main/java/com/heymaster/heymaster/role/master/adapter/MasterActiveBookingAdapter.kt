package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.model.booking.MasterActiveBooking
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class MasterActiveBookingAdapter: ListAdapter<MasterActiveBooking, MasterActiveBookingVH>(ItemMasterActiveBookingDiffCallBack()) {

    inner class MasterActiveBookingVH(private val binding: ItemActiveBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MasterActiveBooking?) {


        }


    }

    private class ItemMasterActiveBookingDiffCallBack: DiffUtil.ItemCallback<MasterActiveBooking>() {
        override fun areItemsTheSame(oldItem: MasterActiveBooking, newItem: MasterActiveBooking): Boolean {
            return oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: MasterActiveBooking,newItem: MasterActiveBooking,): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterActiveBookingVH {
        return MasterActiveBookingVH(ItemActiveBookingMasterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MasterActiveBookingVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }

}