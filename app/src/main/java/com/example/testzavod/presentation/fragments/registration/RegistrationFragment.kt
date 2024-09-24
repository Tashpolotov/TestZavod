package com.example.testzavod.presentation.fragments.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentRegistrationBinding
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.utils.base.BaseFragment
import com.example.testzavod.utils.getTextWatcherForUsername
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun initialize() {
        binding.btnRegister.isEnabled = false // Блокируем кнопку изначально

        binding.etNumber.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && !binding.etNumber.text.toString().startsWith("+")) {
                binding.etNumber.setText("+")
                binding.etNumber.setSelection(binding.etNumber.text!!.length)
            }
        }

        binding.etUserName.addTextChangedListener(getTextWatcherForUsername(binding.etUserName, binding.inputEtUserName))
        binding.etUserName.addTextChangedListener { validateFields() }

        binding.etName.addTextChangedListener(getTextWatcherForUsername(binding.etName, binding.inputEtName))
        binding.etName.addTextChangedListener { validateFields() }

        binding.btnRegister.setOnClickListener {
            binding.progressBar.isVisible = true
            viewModel.sendRegister(
                RegisterSend(
                    phone = binding.etNumber.text.toString(),
                    name = binding.etName.text.toString(),
                    username = binding.etUserName.text.toString()
                )
            )
            viewModel.register.collectUIState(
                state = {
                    binding.progressBar.isVisible = true
                },
                onSuccess = {
                    binding.progressBar.isVisible = false
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                }
            )
        }
    }

    private fun validateFields() {
        val isNameValid = binding.etName.text?.isNotEmpty() == true
        val isUsernameValid = binding.etUserName.text?.isNotEmpty() == true
        val isPhoneValid = binding.etNumber.text?.isNotEmpty() == true

        binding.btnRegister.isEnabled = isNameValid && isUsernameValid && isPhoneValid
    }
}
