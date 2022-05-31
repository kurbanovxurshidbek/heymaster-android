package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemNotificationMessagesBinding
import com.heymaster.heymaster.model.Notification
import com.squareup.picasso.Picasso

class ClientNotificationMessagesAdapter: ListAdapter<Notification, ClientNotificationMessagesAdapter.NotificationMessagesViewHolder>(
    NotificationItemDiffCallback()
) {

    inner class NotificationMessagesViewHolder(private val binding: ItemNotificationMessagesBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {

            binding.tvNotifFullname.text = notification.body
            binding.tvNotifDescrip.text = notification.title
            binding.tvNotifDate.text = notification.data
            Picasso.get().load(notification.image).into(binding.ivProfile)

        }

    }

    private class NotificationItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationMessagesViewHolder {
        val view = ItemNotificationMessagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationMessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationMessagesViewHolder, position: Int) {
        val notificationSuggestion = getItem(position)
        holder.bind(notificationSuggestion)
    }

}