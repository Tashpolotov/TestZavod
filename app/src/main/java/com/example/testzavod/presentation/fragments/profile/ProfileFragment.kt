package com.example.testzavod.presentation.fragments.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.data.model.profile.ProfileData
import com.example.testzavod.databinding.FragmentProfileBinding
import com.example.testzavod.domain.model.profile.ProfileModel
import com.example.testzavod.utils.SharedPref
import com.example.testzavod.utils.base.BaseFragment
import com.example.testzavod.utils.getZodiacSign
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()
    private val sharedPref by lazy { SharedPref(requireContext()) }

    override fun initialize() {

        sharedPref.about?.let {
            binding.tvAbout.text = "О себе: ${it}"
        }
        sharedPref.birthday?.let { birthday ->
            val parts = birthday.split("-")
            if (parts.size >= 3) {
                val day = parts[2].toIntOrNull()
                val month = parts[1].toIntOrNull()

                if (day != null && month != null) {
                    val zodiacSign = getZodiacSign(day, month)
                    binding.tvZodiac.text = "Знак зодиака: $zodiacSign"
                }
            } else {
                binding.tvZodiac.text = "Неверная дата рождения"
            }
        }

        sharedPref.city?.let {
            binding.tvCity.text = "Город: ${it}"
        }
        sharedPref.cameraPhotoPath?.let { photoPath ->
            if (photoPath.isNotEmpty()) {
                val file = File(photoPath)
                if (file.exists()) {
                    binding.ivUserPhoto.setImageURI(Uri.fromFile(file))
                }
            }
        }

        viewModel.profile.collectUIState(
            state = {
                binding.progressBar.isVisible = true
            },
            onSuccess = {
                binding.progressBar.isVisible = false
                binding.tvUserName.text = "Имя: ${it.profileData.name}"
                binding.tvUserSurname.text = "Фамилия: ${it.profileData.username}"
                binding.tvnumber.text = "Номер телефона: ${it.profileData.phone}"

                sharedPref.name = it.profileData.name
                sharedPref.sureName = it.profileData.username
            }
        )
        viewModel.getProfile()

        binding.btnEdit.setOnClickListener {

            findNavController().navigate(R.id.action_nav_profile_to_editFragment)
        }
    }
}