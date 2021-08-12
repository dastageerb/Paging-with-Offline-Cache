package com.example.unsplashimageapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(application: Application,private  val repository: Repository) : AndroidViewModel(application)
{

    /*** Single Responses */
    val singlePhoto: MutableLiveData<NetworkResource<PhotoResponse>> = MutableLiveData();


    fun getSinglePhoto(id:String)
    {
        viewModelScope.launch (Dispatchers.IO)
        {
            getSinglePhotoSafeCall(id)
        } // viewModelScope closed




    } // getSinglePhoto closed

    private suspend fun getSinglePhotoSafeCall(id: String)
    {


        singlePhoto.postValue(NetworkResource.Loading())
        if(getApplication<Application>().hasInternetConnection())
        {
            Timber.tag(TAG).d("has internet")
            try
            {
                val response = repository.remote.getSinglePhoto(id)
                singlePhoto.postValue(handleImageResponse(response))
            }catch (e:Exception)
            {
                singlePhoto.postValue(NetworkResource.Error(e.message))
            } // catch closed

        } // if closed
        else
        {
            singlePhoto.postValue(NetworkResource.Error("No Internet Connection "))
        }

    } // getSinglePhotoSafeCall


    private fun handleImageResponse(response: Response<PhotoResponse>): NetworkResource<PhotoResponse>?
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