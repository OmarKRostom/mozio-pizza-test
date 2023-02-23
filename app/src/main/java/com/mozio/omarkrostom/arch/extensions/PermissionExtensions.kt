package com.mozio.omarkrostom.arch.extensions

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.requestReadStoragePermissions(
    onPermissionAllowed: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED
    ) {
        onPermissionAllowed()
        return
    }
}