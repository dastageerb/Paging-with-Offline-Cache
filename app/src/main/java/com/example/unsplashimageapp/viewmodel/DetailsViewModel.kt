package com.example.unsplashimageapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    application: Application, private val repository: Repository

) : AndroidViewModel(application)
{

   private val _imagesDetailsResponse: MutableStateFlow<NetworkResource<PhotoResponse>> = MutableStateFlow(NetworkResource.Empty())
   val imageDetailsResponse :StateFlow<NetworkResource<PhotoResponse>> get() = _imagesDetailsResponse

     fun getPhotoDetails(id: String) = viewModelScope.launch (Dispatchers.IO)
    {

        if(getApplication<Application>().hasInternetConnection())
        {
            _imagesDetailsResponse.value = NetworkResource.Loading()
            try
            {
                val response = repository.remote.getPhotoDetails(id)
                _imagesDetailsResponse.value = handleImageResponse(response)
            }catch (e: Exception)
            {
                _imagesDetailsResponse.value = NetworkResource.Error(e.message)
            } // catch closed
        }else
        {
            _imagesDetailsResponse.value = NetworkResource.Error("No Internet")
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