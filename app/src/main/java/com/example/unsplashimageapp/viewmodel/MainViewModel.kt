package com.example.unsplashimageapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application,private  val repository: Repository) : AndroidViewModel(application)
{

    /** Getting List Responses */

    val allImages = repository.remote.getPhotos().cachedIn(viewModelScope)

    val currentQuery =  MutableLiveData("")

    val  queriedImages = currentQuery.switchMap()
    {
        Timber.d("it :"+it)
        repository.remote.searchPhotos(it).cachedIn(viewModelScope)
    }

    fun  searchImages(query: String)
    {
        Timber.d(query)
        currentQuery.postValue(query)
    }







} // MainViewModel closed