package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemPopularServicesBinding
import com.heymaster.heymaster.model.Service

class MasterHomePopularServicesAdapter :
    ListAdapter<Service, MasterHomePopularServicesAdapter.PopularServiceViewHolder>(ServiceItemDiffCallback()) {

    lateinit var itemClickListener: (() -> Unit)

    inner class PopularServiceViewHolder(private val binding: ItemPopularServicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {
            binding.itemPopularService.setOnClickListener {
                itemClickListener.invoke()
            }

        }

    }

    private class ServiceItemDiffCallback : DiffUtil.ItemCallback<Service>() {

        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PopularServiceViewHolder {
        val view =
            ItemPopularServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularServiceViewHolder, position: Int) {
        val service = getItem(position)
        holder.bind(service)
    }
}