package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAllServiceBinding
import com.heymaster.heymaster.model.auth.Object

class MasterHomeProfessionsAdapter :
    ListAdapter<Object, MasterHomeProfessionsAdapter.ProfessionViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: (() -> Unit)? = null


    inner class ProfessionViewHolder(private val binding: ItemAllServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Object) {
            binding.tvProfessionName.text = profession.name

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): ProfessionViewHolder {
        val view = ItemAllServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProfessionViewHolder, position: Int
    ) {

    }

}