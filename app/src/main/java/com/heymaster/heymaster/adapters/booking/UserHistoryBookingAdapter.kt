package com.heymaster.heymaster.adapters.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.booking.UserHistoryBookingAdapter.*
import com.heymaster.heymaster.databinding.ItemUserHistoryBookingBinding
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class UserHistoryBookingAdapter :
    ListAdapter<Service, HistoryBookingViewHolder>(ItemHistoryBookingDiffCallback()) {

    inner class HistoryBookingViewHolder(private val binding: ItemUserHistoryBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Service) {

        }
    }

    private class ItemHistoryBookingDiffCallback() : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryBookingViewHolder {
        return HistoryBookingViewHolder(
            ItemUserHistoryBookingBinding.inflate(
                LayoutInflater.from(parent.context
                ),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: HistoryBookingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}