package com.heymaster.heymaster.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemRegionDistrictBinding
import com.heymaster.heymaster.databinding.ItemServiceBinding
import com.heymaster.heymaster.model.Region

class RegionsAdapter: ListAdapter<Region, RegionsAdapter.RegionViewHolder>(RegionItemDiffCallback()) {

    var itemClickListener: ((Region) -> Unit)? = null

    inner class RegionViewHolder(private val binding: ItemRegionDistrictBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(region: Region) {
            binding.tvRegionDistrict.text = region.nameUz


            binding.llRegionDistrict.setOnClickListener {
                itemClickListener?.invoke(region)
            }

        }
    }

    private class RegionItemDiffCallback: DiffUtil.ItemCallback<Region>() {
        override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = ItemRegionDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = getItem(position)
        holder.bind(region)
    }


}