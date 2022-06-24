package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.model.booking.MasterActiveBooking
import com.heymaster.heymaster.model.booking.Object
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class MasterActiveBookingAdapter: ListAdapter<Object, MasterActiveBookingVH>(ItemMasterActiveBookingDiffCallBack()) {

    var acceptListener: ((Object) -> Unit)? = null
    var cancelListener: ((Object) -> Unit)? = null

    inner class MasterActiveBookingVH(private val binding: ItemActiveBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Object) {

            binding.apply {


                if (item.from.fullName != null) {
                    tvNameWorker.text = item.from.fullName
                }
                if (item.from.phoneNumber != null) {
                    tvPhoneNumber.text = item.from.phoneNumber
                }

                tvAccept.setOnClickListener {
                    acceptListener?.invoke(item)
                }

                tvIgnore.setOnClickListener {
                    cancelListener?.invoke(item)
                }

            }


        }


    }

    private class ItemMasterActiveBookingDiffCallBack: DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object,newItem: Object,): Boolean {
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