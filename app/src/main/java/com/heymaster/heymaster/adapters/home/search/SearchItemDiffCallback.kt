package com.heymaster.heymaster.adapters.home.search

import androidx.recyclerview.widget.DiffUtil
import com.heymaster.heymaster.model.SearchItem
import com.heymaster.heymaster.model.User

class SearchItemDiffCallback: DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

}