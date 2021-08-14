package com.example.unsplashimageapp.ui.fragments.detailsFragment.downloadImageService

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import com.example.unsplashimageapp.utils.ExtensionFunction.showToast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class DownloadHelper @Inject constructor(@ApplicationContext private val context: Context) : Target
{

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?)
    {

        try
        {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(path, System.currentTimeMillis().toString() + ".jpg")
            path.mkdirs()
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            context.showToast("Image Downloaded")
        } catch (e: IOException)
        {
            e.printStackTrace()
            context.showToast("Exception " + e.message)
        }
    } // onBitmap loaded closed


    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?)
    {
        context.showToast("Error" + e?.message)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?)
    {
        context.showToast("Downloading")
    }











} // DownloadHelper closed