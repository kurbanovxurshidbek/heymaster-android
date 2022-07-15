package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemActiveBookingBinding
import com.heymaster.heymaster.model.booking.Object
import com.heymaster.heymaster.role.client.adapter.ClientActiveBookingAdapter.*
import com.heymaster.heymaster.utils.Constants.ATTACHMENT_URL
import com.heymaster.heymaster.utils.RandomColor
import com.squareup.picasso.Picasso

class ClientActiveBookingAdapter() : ListAdapter<Object, ClientActiveBookingViewHolder>(ItemActiveBookingDiffCallBack()) {


    var clickFinished : ((Object) -> Unit)? = null

    inner class ClientActiveBookingViewHolder(private val binding: ItemActiveBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activeBooking: Object) {
            binding.apply {
                tvNameWorker.text = activeBooking.toWhom.fullName
                tvPhoneNumber.text = activeBooking.toWhom.phoneNumber
                if (activeBooking.toWhom.profilePhoto != null) {
                    Picasso.get().load(ATTACHMENT_URL + activeBooking.toWhom.profilePhoto).placeholder(RandomColor.randomColor()).into(ivProfilePhoto)
                }

               //   tvJob.text = activeBooking.toWhom.professionList[0].name

                if (activeBooking.isFinished) {
                    tvBookingStatus.text = "Tugadi"
                    tvFinishedActiveBooking.visibility = View.VISIBLE
                } else {
                    tvBookingStatus.text = "Jarayonda"
                    tvFinishedActiveBooking.visibility = View.GONE
                }

                binding.tvFinishedActiveBooking.setOnClickListener {
                    clickFinished?.invoke(activeBooking)
                }


            }

        }
    }

    private class ItemActiveBookingDiffCallBack : DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Object,
            newItem: Object
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientActiveBookingViewHolder {
        return ClientActiveBookingViewHolder(
            ItemActiveBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientActiveBookingViewHolder, position: Int) {
        val activeBooking = getItem(position)
        holder.bind(activeBooking)
    }

}