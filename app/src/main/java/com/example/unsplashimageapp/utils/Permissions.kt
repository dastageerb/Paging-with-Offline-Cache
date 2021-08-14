package com.example.unsplashimageapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.unsplashimageapp.utils.Constants.PERMISSION_REQUEST_CODE
import com.example.unsplashimageapp.utils.Constants.TAG

object Permissions
{

    fun hasPermission(context: Context):Boolean = (ContextCompat.checkSelfPermission(context,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED)


    fun Activity.requestPermission()
    {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    } // requestPermission closed


} /// Permissions closed