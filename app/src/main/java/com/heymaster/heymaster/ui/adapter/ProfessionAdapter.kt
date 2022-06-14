package com.heymaster.heymaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemRegionDistrictBinding
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.ui.adapter.ProfessionAdapter.*

class ProfessionAdapter: ListAdapter<Object, ProfessionViewHolder>(RegionItemDiffCallback()) {

    var itemClickListener: ((Object) -> Unit)? = null

    inner class ProfessionViewHolder(private val binding: ItemRegionDistrictBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Object) {
            binding.tvRegionDistrict.text = profession.name

            binding.llRegionDistrict.setOnClickListener {
                itemClickListener?.invoke(profession)
            }

        }
    }

    private class RegionItemDiffCallback: DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionViewHolder {
        val view = ItemRegionDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }


}