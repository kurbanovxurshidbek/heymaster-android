package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.databinding.ItemHistoryBookingMasterBinding
import com.heymaster.heymaster.model.booking.Object

class MasterHistoryBookingAdapter: ListAdapter<Object, MasterHistoryBookingAdapter.MasterHistoryBookingVH>(ItemMasterHistoryBookingDiffCallBack()) {

    var acceptListener: ((Object) -> Unit)? = null
    var cancelListener: ((Object) -> Unit)? = null

    inner class MasterHistoryBookingVH(private val binding: ItemHistoryBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Object) {

            binding.apply {


                if (item.from.fullName != null) {
                    tvNameWorker.text = item.from.fullName
                }
                if (item.from.phoneNumber != null) {
                    tvPhoneNumber.text = item.from.phoneNumber
                }



            }


        }


    }

    private class ItemMasterHistoryBookingDiffCallBack: DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object,newItem: Object,): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterHistoryBookingVH {
        val view = ItemHistoryBookingMasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MasterHistoryBookingVH(view)
    }

    override fun onBindViewHolder(holder: MasterHistoryBookingVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}