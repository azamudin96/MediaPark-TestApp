package com.mediapark.interview.util

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@TargetApi(Build.VERSION_CODES.M)
object AppPermissionUtil {

    private const val DEFAULT_PERMISSION_REQUEST_CODE = 10000

    // Manifest.permission.MANAGE_DOCUMENTS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE

    @RequiresApi(Build.VERSION_CODES.P)
    val ALL_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACTIVITY_RECOGNITION
    )
    val BASIC_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_WIFI_STATE
    )
    val CAMERA_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    val FILE_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    val CONTACT_PERMISSIONS = arrayOf(
        Manifest.permission.READ_CONTACTS
    )
    val LOCATION_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val BACKGROUND_LOCATION_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    )

    @RequiresApi(Build.VERSION_CODES.P)
    val BIOMETRIC_PERMISSIONS = arrayOf(
        Manifest.permission.USE_BIOMETRIC
    )

    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        if (SystemUtil.compareSDKVersion(Build.VERSION_CODES.M)) {
            for (permission in permissions) {
                if (FILE_PERMISSIONS.contains(permission) &&
                    SystemUtil.compareSDKVersion(Build.VERSION_CODES.Q)
                ) {
                    continue
                }
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED
                )
                    return false
            }
        }
        return true
    }

    fun getNonGrantedPermissions(context: Context, permissions: Array<String>): Array<String> {
        val nonGrantedPermission = mutableListOf<String>()
        if (SystemUtil.compareSDKVersion(Build.VERSION_CODES.M)) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED
                )
                    nonGrantedPermission.add(permission)
            }
        }
        return nonGrantedPermission.toTypedArray()
    }

    fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        launcher: ActivityResultLauncher<Array<out String>>? = null
    ) {
        if (launcher != null) {
            launcher.launch(permissions)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                permissions,
                DEFAULT_PERMISSION_REQUEST_CODE
            )
        }
    }
}