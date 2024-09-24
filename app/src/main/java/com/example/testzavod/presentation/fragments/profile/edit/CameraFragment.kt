package com.example.testzavod.presentation.fragments.profile.edit

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testzavod.R
import com.example.testzavod.databinding.FragmentCameraBinding
import com.example.testzavod.utils.REQUEST_PERMISSION_CAMERA
import com.example.testzavod.utils.REQUEST_PERMISSION_PHOTO
import com.example.testzavod.utils.SharedPref
import com.example.testzavod.utils.base.BaseFragment
import com.example.testzavod.utils.checkPermissionCamera
import com.example.testzavod.utils.checkPermissionPhoto
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    private val binding by viewBinding(FragmentCameraBinding::bind)
    private lateinit var previewView: PreviewView
    private lateinit var imageCapture: ImageCapture
    private var useFrontCamera = false
    private var flashMode = ImageCapture.FLASH_MODE_OFF
    private var selectetFlash = false
    private var currentPhotoPath: String? = null
    private val sharedPref by lazy { SharedPref(requireContext()) }

    override fun initialize() {
        showImagePickerDialog()
        previewView = binding.previewView1

        binding.imgGoPhoto.setOnClickListener {
            capturePhoto()
            selectetFlash = true
        }

        binding.imgSwap.setOnClickListener {
            switchCamera()
        }

        binding.imgFlash.setOnClickListener {
            toggleFlash()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            flashMode = ImageCapture.FLASH_MODE_OFF
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        cameraProvider.unbindAll()
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder()
            .setFlashMode(flashMode)
            .build()

        val cameraSelector = if (useFrontCamera) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }

        try {
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Камера", "Галерея")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите источник изображения")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> clickCamera()
                1 -> clickGallery()
            }
        }
        builder.show()
    }

    private fun clickCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()

        } else {
            checkPermissionCamera(
                permission = Manifest.permission.CAMERA,
                granted = {
                    startCamera()
                },
                denied = {
                    view?.let {
                        findNavController().popBackStack()
                    }
                }
            )
        }
    }

    private fun switchCamera() {
        useFrontCamera = !useFrontCamera
        startCamera()
    }

    private fun toggleFlash() {
        flashMode = when (flashMode) {
            ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
            ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_OFF

            else -> ImageCapture.FLASH_MODE_OFF

        }
        imageCapture?.flashMode = flashMode

    }

    private fun clickGallery() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when (ContextCompat.checkSelfPermission(requireContext(), permission)) {
            PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }

            PackageManager.PERMISSION_DENIED -> {
                checkPermissionPhoto(
                    permission = permission,
                    granted = {
                        openGallery()
                    },
                    denied = {
                        view?.let {
                            findNavController().popBackStack()
                        }
                    }
                )
            }
        }
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            }
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().cacheDir
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun capturePhoto() {
        if (::imageCapture.isInitialized) {
            val photoFile = createImageFile()

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        currentPhotoPath = photoFile.absolutePath
                        sharedPref.cameraPhotoPath = currentPhotoPath
                        val bundle = Bundle().apply {
                            putString("photoPath", currentPhotoPath)
                        }

                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.cameraFragment, true)
                            .build()

                        findNavController().navigate(R.id.editFragment, bundle, navOptions)
                    }

                    override fun onError(exception: ImageCaptureException) {
                    }
                }
            )
        }
    }

    private fun createImageFileFromGallery(imageUri: Uri): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val galleryFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        requireContext().contentResolver.openInputStream(imageUri)?.use { inputStream ->
            galleryFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return galleryFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    val imageUri = data?.data
                    imageUri?.let {
                        val galleryFile = createImageFileFromGallery(it)
                        currentPhotoPath = galleryFile.absolutePath
                        sharedPref.galleryPhotoPath = currentPhotoPath
                        val bundle = Bundle().apply {
                            putString("photoPath", currentPhotoPath)
                        }
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.cameraFragment, true) // Очистка стека до CameraFragment
                            .build()
                        findNavController().navigate(R.id.editFragment, bundle, navOptions)
                    }
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    capturePhoto()
                    startCamera()
                } else {
                    view?.let {
                        findNavController().popBackStack()
                    }
                }
            }

            REQUEST_PERMISSION_PHOTO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    view?.let {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1
        private const val REQUEST_GALLERY = 2
    }

}