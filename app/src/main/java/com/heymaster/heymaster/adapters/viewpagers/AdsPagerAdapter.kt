package com.heymaster.heymaster.adapters.viewpagers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ItemAdsBinding
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.model.Service
import com.squareup.picasso.Picasso

class AdsPagerAdapter: RecyclerView.Adapter<AdsPagerAdapter.AdsViewHolder>(){

    private val ads: ArrayList<Ads> = ArrayList()

inner class AdsViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(ads: Ads) {
        val tvTitle = view.findViewById<TextView>(R.id.tvAdsTitle)

    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)
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
        ads.clear()
        ads.addAll(list)
        notifyDataSetChanged()
    }
}