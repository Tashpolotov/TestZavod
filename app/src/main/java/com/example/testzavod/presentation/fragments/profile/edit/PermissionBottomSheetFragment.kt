package com.example.testzavod.presentation.fragments.profile.edit

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.testzavod.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class PermissionBottomSheetFragment : BottomSheetDialogFragment() {
    private var onSettingsClick: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission_bottom_sheet, container, false)

    }
    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val message = arguments?.getString("message") ?: "Camera and photo access required"
        val settingsText = arguments?.getString("settingsText") ?: ""
        val imageUriString = arguments?.getString("imageUri")

        // Set the title and message
        view.findViewById<TextView>(R.id.tv_title).text = message
        view.findViewById<TextView>(R.id.tv_desc).text = settingsText
        val imageView = view.findViewById<ImageView>(R.id.img_access)
        if (imageUriString != null && imageUriString.isNotEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)
        } else {
            // Set default image resource
            imageView.setImageResource(R.drawable.ic_camera_bsheet)
        }

        // Handle button click to open settings
        view.findViewById<MaterialButton>(R.id.btn_open_settings).setOnClickListener {
            onSettingsClick?.invoke()
            dismiss()
        }
    }

    companion object {
        fun newInstance(message: String, settingsText: String,  imageUri: String, onSettingsClick: () -> Unit): PermissionBottomSheetFragment {
            val fragment = PermissionBottomSheetFragment()
            val args = Bundle().apply {
                putString("message", message)
                putString("settingsText", settingsText)
                putString("imageUri", imageUri)
            }
            fragment.arguments = args
            fragment.onSettingsClick = onSettingsClick
            return fragment
        }
    }
}