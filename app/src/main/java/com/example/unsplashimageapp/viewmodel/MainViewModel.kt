package com.example.unsplashimageapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashimageapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application,private  val repository: Repository) : AndroidViewModel(application)
{

    val getImages = repository.remote.getPhotos().cachedIn(viewModelScope)




}