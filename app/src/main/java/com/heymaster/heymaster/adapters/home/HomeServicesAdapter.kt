package com.heymaster.heymaster.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.home.HomeServicesAdapter.*
import com.heymaster.heymaster.databinding.ItemServiceBinding
import com.heymaster.heymaster.model.Service
import com.squareup.picasso.Picasso

class HomeServicesAdapter: ListAdapter<Service, ServiceViewHolder>(ServiceItemDiffCallback()) {
    inner class ServiceViewHolder(private val binding: ItemServiceBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {
            binding.tvServiceName.text = service.name
            Picasso.get().load(service.image).into(binding.icServices)
        }

    }

    private class ServiceItemDiffCallback: DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = getItem(position)
        holder.bind(service)
    }
}