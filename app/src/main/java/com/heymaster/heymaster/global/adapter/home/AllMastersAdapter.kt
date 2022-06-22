package com.heymaster.heymaster.global.adapter.home

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.R
import com.heymaster.heymaster.databinding.ItemMastersBinding

import com.heymaster.heymaster.model.home.Object

class AllMastersAdapter :
    ListAdapter<Object, AllMastersAdapter.PopularMastersViewHolder>(PopularMasterItemDiffCallback()) {

    lateinit var itemCLickListener: ((Object) -> Unit)

    inner class PopularMastersViewHolder(private val binding: ItemMastersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(master: Object) {

            with(binding) {
                tvMaster.text = master.fullName
                tvDistrict.text = master.location!!.district.nameUz
                tvRegion.text = master.location.region.nameUz
//                ratingMaster.rating = (master.totalMark/master.peopleReitedCount).toFloat()
//                allMarks.text = String.format(allMarks.context.getString(R.string.masters_total_mark), master.totalMark)

                master.professionList.forEach {
                    tvProfession.text = it.name
                }

                root.setOnClickListener {
                    itemCLickListener.invoke(master)
                }
            }


        }

    }

    private class PopularMasterItemDiffCallback : DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMastersViewHolder {
        val view = ItemMastersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularMastersViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMastersViewHolder, position: Int) {
        val master = getItem(position)
        holder.bind(master)
    }
}