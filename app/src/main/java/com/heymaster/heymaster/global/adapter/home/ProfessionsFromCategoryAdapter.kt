package com.heymaster.heymaster.global.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.databinding.ItemProfessionsFromCategoryBinding
import com.heymaster.heymaster.model.auth.Object
import kotlin.random.Random

class ProfessionsFromCategoryAdapter :
    ListAdapter<Object, ProfessionsFromCategoryAdapter.ProfessionViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Object) -> Unit)? = null


    inner class ProfessionViewHolder(private val binding: ItemProfessionsFromCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Object) {
            binding.tvProfessionName.text = profession.name
            binding.tvCategoryName.text = profession.category.name
            binding.ratingProfession.rating = Random.nextInt(1, 5).toFloat()

            binding.root.setOnClickListener {
                itemClickListener?.invoke(profession)
            }

        }

    }

    private class ServiceItemDiffCallback : DiffUtil.ItemCallback<Object>() {

        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProfessionViewHolder {
        val view =
            ItemProfessionsFromCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }
}