package com.smithjilks.mpesaexpensetracker.core.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Permissions {
    private const val REQUEST_CODE_SMS_PERMISSION = 4

    fun requestSmsPermission(context: Activity) {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(context, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(permission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }
}