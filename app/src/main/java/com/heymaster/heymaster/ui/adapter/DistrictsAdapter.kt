package com.heymaster.heymaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemRegionDistrictBinding
import com.heymaster.heymaster.model.District
import com.heymaster.heymaster.ui.adapter.DistrictsAdapter.*

class DistrictsAdapter: ListAdapter<District, DistrictViewHolder>(RegionItemDiffCallback()) {

    var itemClickListener: ((District) -> Unit)? = null

    inner class DistrictViewHolder(private val binding: ItemRegionDistrictBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(district: District) {
            binding.tvRegionDistrict.text = district.nameUz


            binding.llRegionDistrict.setOnClickListener {
                itemClickListener?.invoke(district)
            }

        }
    }

    private class RegionItemDiffCallback: DiffUtil.ItemCallback<District>() {
        override fun areItemsTheSame(oldItem: District, newItem: District): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: District, newItem: District): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val view = ItemRegionDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DistrictViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = getItem(position)
        holder.bind(district)
    }


}