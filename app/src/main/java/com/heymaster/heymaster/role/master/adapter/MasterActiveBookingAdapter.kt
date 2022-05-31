package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class MasterActiveBookingAdapter(private val list: ArrayList<UActiveBookingM>): ListAdapter<UActiveBookingM, MasterActiveBookingVH>(ItemMasterActiveBookingDiffCallBack()) {

    inner class MasterActiveBookingVH(private val binding: ItemActiveBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {

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

    }

    override fun getItemCount(): Int = list.size
}