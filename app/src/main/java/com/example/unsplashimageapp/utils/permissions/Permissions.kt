package com.example.unsplashimageapp.utils.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.unsplashimageapp.utils.Constants.PERMISSION_REQUEST_CODE

object Permissions
{


    fun hasPermission(context: Context):Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
    {
        (ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED)
    } else
    {
        (ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }


    fun Activity.requestPermission()
    {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    } // requestPermission closed


} /// Permissions closed