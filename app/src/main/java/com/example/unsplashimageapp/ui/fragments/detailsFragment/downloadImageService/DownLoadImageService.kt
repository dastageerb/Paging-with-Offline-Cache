package com.example.unsplashimageapp.ui.fragments.detailsFragment.downloadImageService

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleService
import com.example.unsplashimageapp.utils.NetworkResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEmpty
import java.lang.reflect.Array.get
import java.util.concurrent.Flow

class DownLoadImageService : LifecycleService()
{


    private val _imageBitmap:MutableStateFlow<Bitmap>? = null
    private val imageBitmap  get() = _imageBitmap!!
    override fun onCreate()
    {
        super.onCreate()

    } // onCreate closed


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {

        return super.onStartCommand(intent, flags, startId)
    } // onStart command closed

} // DownloadImageService closed