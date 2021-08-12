package com.example.unsplashimageapp.data.remoteDataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.unsplashimageapp.data.UnSplashAllPagingSource
import com.example.unsplashimageapp.data.UnSplashSearchPagingSource
import com.example.unsplashimageapp.data.api.UnSplashApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api:UnSplashApi)
{



    fun getPhotos() = Pager(PagingConfig(20))
    {
        UnSplashAllPagingSource(api)
    }.flow

    fun searchPhotos(query:String) = Pager(PagingConfig(20))
    {
        UnSplashSearchPagingSource(api,query)
    }.flow


    suspend fun getSinglePhoto(id:String) = api.getSinglePhoto(id);













} // RemoteDataSource closed