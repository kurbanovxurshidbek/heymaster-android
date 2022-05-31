package com.heymaster.heymaster.role.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heymaster.heymaster.databinding.ItemNotificationSuggestionsBinding
import com.heymaster.heymaster.model.Notification
import com.squareup.picasso.Picasso

class ClientNotificationSuggestionAdapter: ListAdapter<Notification, ClientNotificationSuggestionAdapter.NotificationSuggestionViewHolder>(
    NotificationItemDiffCallback())  {


    inner class NotificationSuggestionViewHolder(private val binding: ItemNotificationSuggestionsBinding):
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
    ): NotificationSuggestionViewHolder {
        val view = ItemNotificationSuggestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationSuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationSuggestionViewHolder, position: Int) {
        val notificationSuggestion = getItem(position)
        holder.bind(notificationSuggestion)
    }


}