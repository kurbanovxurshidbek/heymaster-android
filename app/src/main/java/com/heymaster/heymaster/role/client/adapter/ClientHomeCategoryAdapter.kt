package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.databinding.ItemRegionDistrictBinding
import com.heymaster.heymaster.role.client.adapter.ClientHomeCategoryAdapter.*
import com.heymaster.heymaster.databinding.ItemServiceBinding
import com.heymaster.heymaster.model.home.Category

class ClientHomeCategoryAdapter: ListAdapter<Category, ServiceViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Category) -> Unit)? = null

    inner class ServiceViewHolder(private val binding: ItemProfessionBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            //Picasso.get().load(category.image).into(binding.icServices)

            binding.itemCategory.setOnClickListener {
                itemClickListener?.invoke(category)
            }

        }
    }

    private class ServiceItemDiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = ItemProfessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}