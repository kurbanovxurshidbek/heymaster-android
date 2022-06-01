package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAllServiceBinding
import com.heymaster.heymaster.model.Service

class MasterHomeAllServicesAdapter : ListAdapter<Service, MasterHomeAllServicesAdapter.AllServiceViewHolder>(ServiceItemDiffCallback()) {

    inner class AllServiceViewHolder(private val binding: ItemAllServiceBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllServiceViewHolder {
        val view = ItemAllServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllServiceViewHolder, position: Int) {
        val service = getItem(position)
        holder.bind(service)
    }
}