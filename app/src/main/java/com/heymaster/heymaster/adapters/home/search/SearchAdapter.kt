package com.heymaster.heymaster.adapters.home.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.adapters.home.search.SearchAdapter.SearchViewHolder
import com.heymaster.heymaster.databinding.ItemSearchBinding
import com.heymaster.heymaster.model.SearchItem


class SearchAdapter : ListAdapter<SearchItem, SearchViewHolder>(SearchItemDiffCallback()) {

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val shopItem = getItem(position)
            binding.tvTitle.text = shopItem.title
            binding.tvBody.text = shopItem.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(position)


}