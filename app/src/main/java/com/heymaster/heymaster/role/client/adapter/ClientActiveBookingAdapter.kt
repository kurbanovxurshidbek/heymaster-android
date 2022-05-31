package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter.ActiveBookingViewHolder
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.Service

class ClientActiveBookingAdapter: ListAdapter<Service,ActiveBookingViewHolder>(
    ItemActiveBookingDiffCallBack()) {

    inner class ActiveBookingViewHolder(private val binding: ItemActiveBookingBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Service) {

        }
    }

    private class ItemActiveBookingDiffCallBack: DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame( oldItem: Service,newItem: Service): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveBookingViewHolder {
        return ActiveBookingViewHolder(ItemActiveBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ActiveBookingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}