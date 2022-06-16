package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.role.client.adapter.ClientHomePopularMastersAdapter.*
import com.heymaster.heymaster.databinding.ItemPopularMastersBinding
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.auth.CurrentUser
import com.heymaster.heymaster.model.home.TopMasters

class ClientHomePopularMastersAdapter :
    ListAdapter<TopMasters, PopularMastersViewHolder>(PopularMasterItemDiffCallback()) {

    lateinit var itemCLickListener: ((TopMasters) -> Unit)

    inner class PopularMastersViewHolder(private val binding: ItemPopularMastersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(master: TopMasters) {

            binding.tvMaster.text = master.fullName
            binding.tvDistrict.text = master.location!!.district.nameUz
            binding.tvRegion.text = master.location.region.nameUz
            binding.ratingMaster.rating = master.peopleReitedCount.toFloat()

            binding.itemCard.setOnClickListener {
                itemCLickListener.invoke(master)
            }



        }

    }

    private class PopularMasterItemDiffCallback : DiffUtil.ItemCallback<TopMasters>() {
        override fun areItemsTheSame(oldItem: TopMasters, newItem: TopMasters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopMasters, newItem: TopMasters): Boolean {
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