package com.example.unsplashimageapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.ui.fragments.detailsFragment.downloadImageService.DownloadHelper
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.NetworkResource
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(application: Application
                                           , private val repository: Repository
                                           ,private val downloadHelper: DownloadHelper
) : AndroidViewModel(application)
{



     fun getPhotoDetails(id: String)  = flow<NetworkResource<PhotoResponse>>()
    {

        if(getApplication<Application>().hasInternetConnection())
        {
            emit(NetworkResource.Loading())
            try
            {
                val response = repository.remote.getPhotoDetails(id)
                emit(handleImageResponse(response))
            }catch (e: Exception)
            {
                emit(NetworkResource.Error(e.message))
            } // catch closed
        }else
        {
            emit(NetworkResource.Error("No Internet"))
        } // else closed


    } // getSinglePhoto closed


    private fun handleImageResponse(response: Response<PhotoResponse>): NetworkResource<PhotoResponse>
    {
        return when
        {
            response.isSuccessful && response.body()!=null  -> NetworkResource.Success(response.body()!!)
            response.code() == 400 -> NetworkResource.Error("Bad Request")
            response.code() == 401 -> NetworkResource.Error("No Access Granted")
            response.code() == 403 -> NetworkResource.Error("Permission is missing")
            response.code() == 404 -> NetworkResource.Error("Could Not Found")
            else -> NetworkResource.Error("Something went wrong ")
        } // when closed
    } // handleImageResponse closed


//    @SuppressLint("BinaryOperationInTimber")
//    @ExperimentalCoroutinesApi
//    fun downloadPhoto(url: String)
//    {
//            try
//            {
//                Picasso.get().load(url).into(downloadHelper)
//            }catch (e: Exception)
//            {
//                Timber.tag(TAG).d(""+e.message)
//            } // catch closed
//
//    } // downloadPhoto closed



    @SuppressLint("BinaryOperationInTimber")
    @ExperimentalCoroutinesApi
    suspend fun download(url: String)
    {
        try
        {
            val response = repository.remote.downloadPhoto(url)
            downloadImage(response)
            Timber.tag(TAG).d("tim")
        }catch (e: Exception)
        {
            Timber.tag(TAG).d(""+e.message)
        } // catch closed

    } // downloadPhoto closed




    @Throws(IOException::class)
    private fun downloadImage(body: ResponseBody)
    {
        var count: Int
        val data = ByteArray(1024 * 4)
        val fileSize = body.contentLength()
        Timber.tag(TAG).d(""+fileSize)

        val inputStream: InputStream = BufferedInputStream(body.byteStream(), 1024 * 8)


        val outputFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis().toString() + ".jpg")
        val outputStream: OutputStream = FileOutputStream(outputFile)



        while (inputStream.read(data).also { count = it } != -1)
       {
            outputStream.write(data, 0, count)
       }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }











} // DetailsViewModel