package com.example.testzavod.presentation.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testzavod.databinding.ItemMessage2Binding
import com.example.testzavod.databinding.ItemMessageBinding

data class Message(val id: String, val content: String, val isSentByMe: Boolean)

class MessagesAdapter(
    private val onClick: (Message) -> Unit
) : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    private val VIEW_TYPE_ME = 1
    private val VIEW_TYPE_FRIEND = 2

    inner class MeMessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessageMe.text = message.content
            itemView.setOnClickListener {
                onClick(message)
            }
        }
    }

    inner class FriendMessageViewHolder(private val binding: ItemMessage2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessageFriend.text = message.content
            itemView.setOnClickListener {
                onClick(message)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ME) {
            MeMessageViewHolder(
                ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            FriendMessageViewHolder(
                ItemMessage2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is MeMessageViewHolder) {
            holder.bind(message)
        } else if (holder is FriendMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSentByMe) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_FRIEND
        }
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}
