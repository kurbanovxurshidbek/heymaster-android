package com.heymaster.heymaster.role.client.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.booking.ClientActiveBooking
import com.heymaster.heymaster.model.booking.Object
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter.*

class ClientActiveBookingAdapter() : ListAdapter<Object, ClientActiveBookingViewHolder>(ItemActiveBookingDiffCallBack()) {


    var clickFinished : ((Object) -> Unit)? = null

    inner class ClientActiveBookingViewHolder(private val binding: ItemActiveBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activeBooking: Object) {
            binding.apply {
                tvNameWorker.text = activeBooking.toWhom.fullName

                binding.tvFinishedActiveBooking.setOnClickListener {
                    clickFinished?.invoke(activeBooking)
                }


            }

        }
    }

    private class ItemActiveBookingDiffCallBack : DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Object,
            newItem: Object
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientActiveBookingViewHolder {
        return ClientActiveBookingViewHolder(
            ItemActiveBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientActiveBookingViewHolder, position: Int) {
        val activeBooking = getItem(position)
        holder.bind(activeBooking)
    }

}