package com.example.unsplashimageapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.unsplashimageapp.data.api.ImagesRepository
import com.example.unsplashimageapp.data.local.ImageDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel(repository: ImagesRepository) :ViewModel(){


    private val _allImages = MutableStateFlow<PagingData<ImageDbEntity>>(PagingData.empty())
    val getAllImages = _allImages


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllImages().collect {
                _allImages.value = it
            }
        }

    }

}