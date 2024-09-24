package com.example.testzavod.presentation.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentMainBinding
import com.example.testzavod.utils.base.BaseFragment

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val adapter = ChatAdapter(this::onClick)

    override fun initialize() {
        binding.rvChat.adapter = adapter
        adapter.submitList(chatUsers)
    }

    private fun onClick(chatUser: ChatUser) {
        val bundle = Bundle().apply {
            putString("id", chatUser.id)
        }
        findNavController().navigate(R.id.action_nav_main_to_chatsFragment, bundle)
    }

    private val chatUsers = listOf(
        ChatUser("1", "Азамат", "url1", "Привет!"),
        ChatUser("2", "Вася", "url2", "Как деда?"),
        ChatUser("3", "Игорь", "url3", "Доброе утро")
    )

}