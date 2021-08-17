package com.example.unsplashimageapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.AndroidViewModel

import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.Constants.IMAGE_URL
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    application: Application, private val repository: Repository, @ApplicationContext context: Context

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




















} // DetailsViewModel