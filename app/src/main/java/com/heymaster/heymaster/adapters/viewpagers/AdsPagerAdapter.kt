package com.heymaster.heymaster.adapters.viewpagers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemAdsBinding
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.model.Service
import com.squareup.picasso.Picasso

class AdsPagerAdapter: RecyclerView.Adapter<AdsPagerAdapter.AdsViewHolder>(){

    private val ads: ArrayList<Ads> = ArrayList()

inner class AdsViewHolder(private val binding: ItemAdsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(ads: Ads) {
        binding.tvAdsTitle.text = ads.title


    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val view = ItemAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        val ads = ads[position]
        holder.bind(ads)
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    fun submitAds(list: List<Ads>) {
        ads.addAll(list)
        notifyDataSetChanged()
    }
}