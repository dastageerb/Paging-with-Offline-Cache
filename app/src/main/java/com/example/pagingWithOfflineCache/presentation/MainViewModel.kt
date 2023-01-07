package com.example.pagingWithOfflineCache.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingWithOfflineCache.domain.ImageEntity
import com.example.pagingWithOfflineCache.domain.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: ImagesRepository) :ViewModel(){
    private val _allImages = MutableStateFlow<PagingData<ImageEntity>>(PagingData.empty())
    val getAllImages = _allImages
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllImages().cachedIn(viewModelScope).collect {
                _allImages.value = it
            }
        }
    }
}