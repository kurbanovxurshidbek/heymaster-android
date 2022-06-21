package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemMastersBinding

import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.home.HomeResponse
import com.heymaster.heymaster.model.home.Object
import com.heymaster.heymaster.model.home.TopMasters

class MasterHomePopularMasterAdapter : ListAdapter<Object, MasterHomePopularMasterAdapter.PopularMastersViewHolder>(PopularMasterItemDiffCallback()) {

    lateinit var itemCLickListener: ((Object) -> Unit)

    inner class PopularMastersViewHolder(private val binding: ItemMastersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(master: Object) {

            binding.tvMaster.text = master.fullName
            binding.ratingMaster.rating = master.totalMark.toFloat()
            binding.tvDistrict.text = master.location!!.district.nameUz
            binding.tvRegion.text = master.location.region.nameUz
            binding.allMarks.text ="all reviews: ${master.peopleReitedCount.toString()}"

            binding.itemCard.setOnClickListener {
                itemCLickListener.invoke(master)
            }
        }

    }

    private class PopularMasterItemDiffCallback : DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMastersViewHolder {
        val view = ItemMastersBinding.inflate(
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