package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAllServiceBinding
import com.heymaster.heymaster.role.client.adapter.ClientHomeProfessionsAdapter.ProfessionViewHolder
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.model.auth.Profession

class ClientHomeProfessionsAdapter :
    ListAdapter<Object, ProfessionViewHolder>(ServiceItemDiffCallback()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProfessionViewHolder {
        val view =
            ItemAllServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }
}