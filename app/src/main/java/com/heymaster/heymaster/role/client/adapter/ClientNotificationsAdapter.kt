package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemNotificationMessagesBinding
import com.heymaster.heymaster.model.home.ObjectX

class ClientNotificationsAdapter :
    ListAdapter<ObjectX, ClientNotificationsAdapter.NotificationMessagesViewHolder>(
        NotificationItemDiffCallback()
    ) {

    inner class NotificationMessagesViewHolder(private val binding: ItemNotificationMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: ObjectX) {
            binding.apply {
                tvNotifFullname.text = notification.toWhom.fullName
                tvNotifDescrip.text = notification.body
                tvNotifDate.text = notification.createAt
            }


        }

    }

    private class NotificationItemDiffCallback : DiffUtil.ItemCallback<ObjectX>() {
        override fun areItemsTheSame(oldItem: ObjectX, newItem: ObjectX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ObjectX, newItem: ObjectX): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationMessagesViewHolder {
        val view = ItemNotificationMessagesBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return NotificationMessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationMessagesViewHolder, position: Int) {
        val notificationSuggestion = getItem(position)
        holder.bind(notificationSuggestion)
    }

}