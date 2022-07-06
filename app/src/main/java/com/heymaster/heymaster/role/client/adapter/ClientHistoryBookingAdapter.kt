package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemUserHistoryBookingBinding
import com.heymaster.heymaster.role.client.adapter.ClientHistoryBookingAdapter.*
import com.heymaster.heymaster.model.booking.Object

class ClientHistoryBookingAdapter : ListAdapter<Object, ClientHistoryBookingViewHolder>(ItemActiveBookingDiffCallBack()) {


    var clickRate : ((Object) -> Unit)? = null

    inner class ClientHistoryBookingViewHolder(private val binding: ItemUserHistoryBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: Object) {
            binding.apply {
                tvNameWorker.text = history.toWhom.fullName
                tvPhoneNumber.text = history.toWhom.phoneNumber
                tvBookingStatus.text = "Tugagan"




                tvAccept.setOnClickListener {
                    clickRate?.invoke(history)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHistoryBookingViewHolder {
        return ClientHistoryBookingViewHolder(
            ItemUserHistoryBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientHistoryBookingViewHolder, position: Int) {
        val activeBooking = getItem(position)
        holder.bind(activeBooking)
    }

}