package com.heymaster.heymaster.global.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAllCategoryBinding
import com.heymaster.heymaster.model.home.Category
import kotlin.random.Random

class AllCategoryAdapter: ListAdapter<Category, AllCategoryAdapter.ServiceViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Category) -> Unit)? = null

    inner class ServiceViewHolder(private val binding: ItemAllCategoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvNameCategory.text = category.name
            binding.ratingCategory.rating = Random.nextInt(1,5).toFloat()
            binding.root.setOnClickListener {
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
        val view = ItemAllCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}