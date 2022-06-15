package com.heymaster.heymaster.role.master.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemServiceBinding
import com.heymaster.heymaster.model.home.Category


class   MasterHomeServicesAdapter : ListAdapter<Category, MasterHomeServicesAdapter.CategoryViewHolder>(CategoryItemDiffCallback()) {

    var itemClickListener: ((Category) -> Unit)? = null

    inner class CategoryViewHolder(private val binding: ItemServiceBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvServiceName.text = category.name
            //Picasso.get().load(category.image).into(binding.icServices)

            binding.llItemService.setOnClickListener {
                itemClickListener?.invoke(category)
            }

        }
    }

    private class CategoryItemDiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}