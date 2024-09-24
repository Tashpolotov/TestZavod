package com.example.testzavod.presentation.fragments.profile.edit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentEditBinding
import com.example.testzavod.domain.model.profile.Avatar
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.presentation.fragments.profile.ProfileViewModel
import com.example.testzavod.utils.SharedPref
import com.example.testzavod.utils.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditFragment : BaseFragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()
    private var userBirthday: String? = null
    private val sharedPref by lazy { SharedPref(requireContext()) }
    private var name: String? = null
    private var sureName: String? = null
    private var currentPhotoPath: String? = null

    override fun initialize() {
        name = sharedPref.name
        sureName = sharedPref.sureName
        val photoPath = arguments?.getString("photoPath")
        if (photoPath != null) {
            Log.d("EditFragment", "Received photo path: $photoPath")
            currentPhotoPath = photoPath
            loadImageIntoView(photoPath)
        }

        binding.containerUserPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_cameraFragment)
        }

        binding.etUserBirthday.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val date = Date(selection)
                userBirthday = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                binding.etUserBirthday.setText(userBirthday)
            }
        }

        binding.btnSave.setOnClickListener {
            val userCity = binding.etUserCity.text.toString()
            sharedPref.about = binding.etUserAbout.text.toString()
            sharedPref.birthday = binding.etUserBirthday.text.toString()
            sharedPref.city = userCity

            sendDataToServer(userCity, userBirthday)
            findNavController().navigate(R.id.action_editFragment_to_nav_profile)
        }
    }

    private fun sendDataToServer(city: String, birthday: String?) {
        viewModel.change.collectUIState(
            state = {
                binding.progressBar.isVisible = true
            },
            onSuccess = {
                binding.progressBar.isVisible = false
            },
        )

        val base64Image = currentPhotoPath?.let { encodeImageToBase64(it) }

        val changeData = ChangeData(
            birthday = birthday.orEmpty(),
            name = name,
            username = sureName,
            city = city,
            avatar = Avatar(
                filename = "aza",
                base_64 = base64Image.orEmpty()
            )
        )
        viewModel.sendChange(changeData)
    }

    private fun encodeImageToBase64(filePath: String): String {
        val bitmap = BitmapFactory.decodeFile(filePath)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun loadImageIntoView(photoPath: String) {
        Glide.with(this).load(photoPath).into(binding.ivUserPhoto)
    }
}
