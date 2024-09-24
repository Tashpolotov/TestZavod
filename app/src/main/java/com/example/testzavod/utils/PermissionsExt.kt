package com.example.testzavod.utils

import androidx.fragment.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.example.testzavod.R
import com.example.testzavod.presentation.fragments.profile.edit.PermissionBottomSheetFragment

const val REQUEST_PERMISSION_PHOTO = 123
const val REQUEST_PERMISSION_CAMERA = 124

fun Fragment.checkPermissionPhoto(permission: String, granted: () -> Unit, denied: () -> Unit) {
    val mContext = context ?: return
    when(ContextCompat.checkSelfPermission (mContext, permission)) {
        PackageManager.PERMISSION_GRANTED -> granted()
        else -> {
            if (shouldShowRequestPermissionRationale(permission)) {
                val bottomSheet = PermissionBottomSheetFragment.newInstance(
                    "Вам нужно дать доступ к камере",
                    "без доступ к камере нельзя будет им пользоваться",
                    "android.resource://${requireContext().packageName}/${R.drawable.ic_camera_bsheet}",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", requireContext().packageName, null)
                        }
                        startActivity(intent)
                    })
                bottomSheet.show(parentFragmentManager, "CustomBottomSheet")
            } else {
                requestPermissions(arrayOf(permission), REQUEST_PERMISSION_PHOTO)
            }
        }
    }
}

fun Fragment.checkPermissionCamera(permission: String, granted: () -> Unit, denied: () -> Unit) {
    val mContext = context ?: return
    when(ContextCompat.checkSelfPermission(mContext, permission)) {
        PackageManager.PERMISSION_GRANTED -> granted()
        else -> {
            if (shouldShowRequestPermissionRationale(permission)) {
                val bottomSheet = PermissionBottomSheetFragment.newInstance(
                    "Вам нужно дайть доступ к галере",
                    "TБез доступа к галерее нельзя будет пользоваться им",
                    "android.resource://${requireContext().packageName}/${R.drawable.ic_camera_bsheet}",
                    {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", requireContext().packageName, null)
                        }
                        startActivity(intent)
                    })
                bottomSheet.show(parentFragmentManager, "CustomBottomSheet")
            } else {
                requestPermissions(arrayOf(permission), REQUEST_PERMISSION_CAMERA)
            }
        }
    }
}


