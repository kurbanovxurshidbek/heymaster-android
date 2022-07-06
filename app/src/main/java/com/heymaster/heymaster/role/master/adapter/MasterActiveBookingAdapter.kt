package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.master.adapter.MasterActiveBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemActiveBookingMasterBinding
import com.heymaster.heymaster.model.booking.Object

class MasterActiveBookingAdapter: ListAdapter<Object, MasterActiveBookingVH>(ItemMasterActiveBookingDiffCallBack()) {

    var acceptListener: ((Object) -> Unit)? = null
    var cancelListener: ((Object) -> Unit)? = null
    var finishedListener: ((Object) -> Unit)? = null

    inner class MasterActiveBookingVH(private val binding: ItemActiveBookingMasterBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Object) {

            binding.apply {
                    tvNameWorker.text = item.from.fullName
                    tvPhoneNumber.text = item.from.phoneNumber

                    if (item.accepted) {
                        tvBookingStatus.visibility = View.VISIBLE
                        tvFinish.visibility = View.VISIBLE
                        tvAccept.visibility = View.GONE
                    }

                    tvFinish.setOnClickListener {
                        finishedListener?.invoke(item)
                    }


                    tvAccept.setOnClickListener {
                        acceptListener?.invoke(item)
                    }

                    tvCancel.setOnClickListener {
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