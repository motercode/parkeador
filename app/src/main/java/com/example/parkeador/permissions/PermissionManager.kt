package com.example.parkeador.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) {

    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.NEARBY_WIFI_DEVICES
    )

    fun requestPermissions(launcher: ActivityResultLauncher<Array<String>>) {
        val permissionsToRequest = mutableListOf<String>()
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            launcher.launch(permissionsToRequest.toTypedArray())
        }
    }

    fun handlePermissionsResult(permissions: Map<String, Boolean>): Boolean {
        return permissions.values.all { it }
    }
}
