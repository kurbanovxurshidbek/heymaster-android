package com.heymaster.heymaster.role.client.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.user_booking.UActiveBookingM

class ClientActiveBookingAdapter(private val list: ArrayList<UActiveBookingM>, var context: Context) :
    ListAdapter<UActiveBookingM, ClientActiveBookingAdapter.ActiveBookingVH>(ItemActiveBookingDiffCallBack()) {

    private lateinit var itemClickListener: ItemClickListener

    inner class ActiveBookingVH(private val binding: ItemActiveBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userActiveBooking: UActiveBookingM) {
            binding.tvJob.text = userActiveBooking.jobType
            binding.imProfilePhoto.setImageResource(userActiveBooking.profileImg)
            binding.tvNameWorker.text = userActiveBooking.nameEmp
            binding.tvDateBooking.text = userActiveBooking.orderDate
            binding.tvPhoneNumber.text = userActiveBooking.phoneNumber
            binding.tvFinishedActiveBooking.setOnClickListener {
                itemClickListener.onClick(userActiveBooking)
            }

        }
    }

    private class ItemActiveBookingDiffCallBack : DiffUtil.ItemCallback<UActiveBookingM>() {
        override fun areItemsTheSame(oldItem: UActiveBookingM, newItem: UActiveBookingM): Boolean {
            return oldItem.idEmp == newItem.idEmp
        }

        override fun areContentsTheSame(
            oldItem: UActiveBookingM,
            newItem: UActiveBookingM
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveBookingVH {
        return ActiveBookingVH(
            ItemActiveBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActiveBookingVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    class ItemClickListener(val onClick: (item: UActiveBookingM) -> Unit)

}