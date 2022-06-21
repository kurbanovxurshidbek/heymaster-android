package com.heymaster.heymaster.role.master.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ItemMasterPortfolioPlusImageBinding
import com.heymaster.heymaster.databinding.ItemPortfolioBinding
import com.heymaster.heymaster.model.AttachmentInfo
import com.heymaster.heymaster.model.masterprofile.Portfolio
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.heymaster.heymaster.utils.RandomColor
import com.squareup.picasso.Picasso
import kotlin.math.log

class MasterPortfolioAdapter : ListAdapter<Portfolio, RecyclerView.ViewHolder>(PortfolioItemDiffCallback()) {

    companion object {
        var addItemClick: (() -> Unit)? = null
    }


    sealed class PortfolioViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        class AddViewHolder(private val binding: ItemMasterPortfolioPlusImageBinding): PortfolioViewHolder(binding) {
            fun bind() {
                binding.root.setOnClickListener {
                    addItemClick?.invoke()
                }

            }
        }

        class ImageViewHolder(private val binding: ItemPortfolioBinding): PortfolioViewHolder(binding) {
            fun bind(image: Portfolio.Image){
                Glide.with(binding.itemImages).load(ATTACHMENT_URL + image.attachmentInfo.id).placeholder(RandomColor.randomColor()).into(binding.itemImages)

                binding.root.setOnClickListener {
                    Log.d("@@@333", "bind: ${image.id}")
                }
            }
        }
    }

    class PortfolioItemDiffCallback: DiffUtil.ItemCallback<Portfolio>() {
        override fun areItemsTheSame(oldItem: Portfolio, newItem: Portfolio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Portfolio, newItem: Portfolio): Boolean {
            return oldItem == newItem
        }
    }

    fun addItemClickListener(listener: () -> Unit) {
         addItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return when(viewType) {
            R.layout.item_master_portfolio_plus_image -> PortfolioViewHolder.AddViewHolder(
                ItemMasterPortfolioPlusImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            R.layout.item_portfolio -> PortfolioViewHolder.ImageViewHolder(ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            else -> throw IllegalArgumentException("Invalid ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is PortfolioViewHolder.AddViewHolder -> {
                holder.bind()
            }


            is PortfolioViewHolder.ImageViewHolder -> {
                Log.d("TAG", "onBindViewHolder: fd")
                holder.bind(currentList[position] as Portfolio.Image)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("TAG,", "getItemViewType: 2")
        if (position == 0) {
            Log.d("TAG", "getItemViewType: ll")
            return R.layout.item_master_portfolio_plus_image
        }
        return R.layout.item_portfolio
    }


}

