package com.example.unsplashimageapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(application: Application,private  val repository: Repository) : AndroidViewModel(application)
{

    /** Getting All photo Response */

    private val _getResponse: MutableStateFlow<NetworkResource<PagingData<UnSplashResponseItem>>> = MutableStateFlow(NetworkResource.Empty())
    val getResponse :StateFlow<NetworkResource<PagingData<UnSplashResponseItem>>> get() = _getResponse

    /** Search All photos request */
    fun requestPhotos() = viewModelScope.launch(Dispatchers.IO)
    {
        _getResponse.value = NetworkResource.Loading()
        if (getApplication<Application>().hasInternetConnection())
        {

           val response =  repository.remote.getAllPhotos().cachedIn(viewModelScope)

            response.catch()
            {
                exception ->
                _getResponse.value = NetworkResource.Error(exception.message)
            }
                .collect()
            {
                    _getResponse.value  = NetworkResource.Success(it)
            } // collect closed
        }
        else
        {
            _getResponse.value = NetworkResource.Error("No Internet")
        } // else closed
    } // requestAllPhoto



    /** Search Query Request */

    fun requestPhotos(query:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _getResponse.value = NetworkResource.Loading()
        if (getApplication<Application>().hasInternetConnection())
        {
            val response =  repository.remote.searchPhotos(query).cachedIn(viewModelScope)
            response.collect()
            {
                _getResponse.value  = NetworkResource.Success(it)
            } // collect closed
        }
        else
        {
            _getResponse.value = NetworkResource.Error("No Internet")
        } // else closed
    } // requestAllPhoto







} // MainViewModel closed