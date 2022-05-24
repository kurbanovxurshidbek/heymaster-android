package com.heymaster.heymaster.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.home.HomePopularMastersAdapter.*
import com.heymaster.heymaster.databinding.ItemAdsBinding
import com.heymaster.heymaster.databinding.ItemPopularMastersBinding
import com.heymaster.heymaster.model.User

class HomePopularMastersAdapter:
    ListAdapter<User, PopularMastersViewHolder>(PopularMasterItemDiffCallback()) {

    inner class PopularMastersViewHolder(private val binding: ItemPopularMastersBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(master: User) {

        }

    }

    private class PopularMasterItemDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
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