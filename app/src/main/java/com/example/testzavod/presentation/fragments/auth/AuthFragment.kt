package com.example.testzavod.presentation.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentAuthBinding
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.utils.base.BaseFragment
import com.example.testzavod.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun initialize() {
        binding.btnHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
        }

        visible()
        binding.ccp.setDefaultCountryUsingNameCode("US")
        binding.ccp.registerCarrierNumberEditText(binding.etNumber)

        binding.btnAuth.setOnClickListener {
            val phoneNumber = binding.ccp.fullNumberWithPlus
            val code = binding.etSmsCode.text.toString()

            when (binding.btnAuth.text) {
                getString(R.string.auth) -> {
                    showLoading()
                    if (phoneNumber.isNotBlank()) {
                        viewModel.sendAuth(AuthPhone(phone = phoneNumber))
                    } else {
                        context?.showToast("Введите номер телефона")
                    }
                }
                getString(R.string.send_code) -> {
                    showLoading()
                    if (code.isNotBlank()) {
                        viewModel.sendCode(AuthCode(phone = phoneNumber, code = code))
                    } else {
                        context?.showToast("Введите код из SMS")
                    }
                }
            }
        }
    }

    private fun visible() {
        viewModel.code.collectUIState(
            state = {
            },
            success = {
                hideLoading()
                binding.progressBar.isVisible = false
                findNavController().navigate(R.id.action_authFragment_to_mainFragment)
            }
        )

        viewModel.auth.collectUIState(
            state = {
                // можно добавить загрузку
            },
            success = {
                if (it.is_success) {
                    hideLoading()
                    binding.progressBar.isVisible = false
                    binding.inputEtSmsCode.visibility = View.VISIBLE
                    binding.btnAuth.text = getString(R.string.send_code)
                    isHidden
                } else {
                    context?.showToast("Неверный телефон")
                }
            }
        )
    }
}
