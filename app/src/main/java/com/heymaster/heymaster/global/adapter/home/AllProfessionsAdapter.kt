package com.heymaster.heymaster.global.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAllProfessionBinding
import com.heymaster.heymaster.databinding.ItemAllServiceBinding
import com.heymaster.heymaster.model.auth.Object
import kotlin.random.Random

class AllProfessionsAdapter: ListAdapter<Object, AllProfessionsAdapter.AllServiceViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Object) -> Unit)? = null
    inner class AllServiceViewHolder(private val binding: ItemAllProfessionBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Object) {
            with(binding) {
                tvProfessionName.text = profession.name
                tvCategoryName.text = profession.category.name
                ratingProfession.rating = Random.nextInt(1, 5).toFloat()

                root.setOnClickListener {
                    itemClickListener?.invoke(profession)
                }
            }




        }

    }

    private class ServiceItemDiffCallback: DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllServiceViewHolder {
        val view = ItemAllProfessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllServiceViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }
}