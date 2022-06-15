package com.heymaster.heymaster.role.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.model.auth.Profession

class MasterHomePopularServicesAdapter :
    ListAdapter<Profession, MasterHomePopularServicesAdapter.ProfessionsViewHolder>(ServiceItemDiffCallback()) {

    lateinit var itemClickListener: (() -> Unit)

    inner class ProfessionsViewHolder(private val binding: ItemProfessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Profession) {
            binding.itemCategory.setOnClickListener {
                itemClickListener.invoke()
            }

        }

    }

    private class ServiceItemDiffCallback : DiffUtil.ItemCallback<Profession>() {

        override fun areItemsTheSame(oldItem: Profession, newItem: Profession): Boolean {
            return oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: Profession, newItem: Profession): Boolean {
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