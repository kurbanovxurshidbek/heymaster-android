package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemPopularMastersBinding
import com.heymaster.heymaster.model.Service

class MasterHomePopularMasterAdapter : ListAdapter<Service, MasterHomePopularMasterAdapter.PopularMastersViewHolder>(PopularMasterItemDiffCallback()) {

    lateinit var itemCLickListener: (() -> Unit)

    inner class PopularMastersViewHolder(private val binding: ItemPopularMastersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(master: Service) {
            binding.itemCard.setOnClickListener {
                itemCLickListener.invoke()
            }
        }

    }

    private class PopularMasterItemDiffCallback : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMastersViewHolder {
        val view = ItemPopularMastersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularMastersViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMastersViewHolder, position: Int) {
        val master = getItem(position)
        holder.bind(master)
    }
}