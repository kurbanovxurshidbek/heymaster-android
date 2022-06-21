package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.model.auth.Profession
import com.heymaster.heymaster.model.home.Category

class MasterHomePrimeCategoryAdapter :
    ListAdapter<Category, MasterHomePrimeCategoryAdapter.ProfessionsViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Category) -> Unit)? = null

    inner class ProfessionsViewHolder(private val binding: ItemProfessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvProfessionName.text = category.name.toString()

            binding.itemCategory.setOnClickListener {
                itemClickListener?.invoke(category)
            }

        }

    }

    private class ServiceItemDiffCallback : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProfessionsViewHolder {
        val view =
            ItemProfessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessionsViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }
}