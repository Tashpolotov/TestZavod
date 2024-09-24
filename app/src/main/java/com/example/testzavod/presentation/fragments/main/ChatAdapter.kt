package com.example.testzavod.presentation.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testzavod.databinding.ItemMainBinding

data class ChatUser(
    val id:String,
    val name: String,
    val imageUrl: String,
    val message: String
)

class ChatAdapter(
    val onClick:(ChatUser)->Unit
) :ListAdapter<ChatUser, ChatAdapter.ChatViewHolder>(ChatDiff()){

   inner class ChatViewHolder(private val binding:ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
       fun bind(item: ChatUser) {
           binding.tvNameUser.text = item.name
           binding.tvMessageUser.text = item.message
           itemView.setOnClickListener {
               onClick(item)
           }
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChatDiff: DiffUtil.ItemCallback<ChatUser>() {
    override fun areItemsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
        return oldItem == newItem
    }

}