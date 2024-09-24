package com.example.testzavod.presentation.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentChatsBinding
import com.example.testzavod.utils.base.BaseFragment

class ChatsFragment : BaseFragment(R.layout.fragment_chats) {

    private val binding by viewBinding(FragmentChatsBinding::bind)
    private val adapter = MessagesAdapter(this::onClick)

    override fun initialize() {
        val id = arguments?.getString("id") ?: return
        val messages = chatMessages[id] ?: emptyList()

        binding.rvMessage.adapter = adapter
        adapter.submitList(messages)

        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString()
            if (messageText.isNotBlank()) {
                val newMessage = Message(
                    id = System.currentTimeMillis().toString(),
                    content = messageText,
                    isSentByMe = true
                )

                chatMessages[id]?.add(newMessage)

                adapter.submitList(chatMessages[id])

                binding.editTextMessage.setText("")
            }
        }
    }

    private fun onClick(message: Message) {

    }

    private val chatMessages = mutableMapOf(
        "1" to mutableListOf(
            Message("1", "Привет!", true),
            Message("2", "Как деларолрдлрдлрдлролрлдрлдрлдрлдрлдрлдрлорлдрлолорлдрлд?", false),
            Message("3", "Все хорошо, ты как?", true),
            Message("4", "Плохо, очень плохо, девушка бросила", false)
        ),
        "2" to mutableListOf(
            Message("5", "Как дела?", true),
            Message("6", "Вася?", true),
            Message("7", "Плохо, сказал же", false)
        ),
        "3" to mutableListOf(
            Message("8", "Доброе утро?", true),
            Message("9", "Не пиши мне", false)
        )
    )

}