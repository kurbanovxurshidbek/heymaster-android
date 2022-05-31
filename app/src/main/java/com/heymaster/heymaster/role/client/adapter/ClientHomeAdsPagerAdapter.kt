package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.model.Ads

class ClientHomeAdsPagerAdapter: RecyclerView.Adapter<ClientHomeAdsPagerAdapter.AdsViewHolder>(){

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