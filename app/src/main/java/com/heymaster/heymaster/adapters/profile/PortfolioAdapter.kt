package com.heymaster.heymaster.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemPortfolioBinding
import com.heymaster.heymaster.databinding.ItemServiceBinding
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.squareup.picasso.Picasso

class PortfolioAdapter : ListAdapter<Portfolio, PortfolioAdapter.ProfileViewHolder>(PortfolioItemDiffCallback()) {



    inner class ProfileViewHolder(private val binding: ItemPortfolioBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Portfolio) {
            Picasso.get().load(profile.image).into(binding.itemImages)
        }

    }


    private class PortfolioItemDiffCallback : DiffUtil.ItemCallback<Portfolio>() {
        override fun areItemsTheSame(oldItem: Portfolio, newItem: Portfolio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Portfolio, newItem: Portfolio): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val portfolio = getItem(position)
        holder.bind(portfolio)
    }


}
