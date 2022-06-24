package com.heymaster.heymaster.global.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heymaster.heymaster.databinding.ItemProfessionBinding
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.squareup.picasso.Picasso

class PopularProfessionsAdapter :
    ListAdapter<Object, PopularProfessionsAdapter.ProfessionViewHolder>(ServiceItemDiffCallback()) {

    var itemClickListener: ((Object) -> Unit)? = null


    inner class ProfessionViewHolder(private val binding: ItemProfessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profession: Object) {
            binding.apply {
                tvProfessionName.text = profession.name
                Picasso.get().load(profession.photoUrl).into(ivProfession)

            }


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
            ItemProfessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = getItem(position)
        holder.bind(profession)
    }
}