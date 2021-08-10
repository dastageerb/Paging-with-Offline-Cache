package com.example.unsplashimageapp.data.remoteDataSource

import androidx.paging.Config
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.unsplashimageapp.data.Entity.UnSplashResponseItem
import com.example.unsplashimageapp.data.UnSplashPagingSource
import com.example.unsplashimageapp.data.api.UnSplashApi
import com.example.unsplashimageapp.utils.NetworkResource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api:UnSplashApi)
{



    fun getPhotos() = Pager(PagingConfig(20))
    {
        UnSplashPagingSource(api)
    }.liveData

    fun searchPhotos(query:String) = Pager(PagingConfig(20))
    {
        UnSplashPagingSource(api,query)
    }.liveData


} // RemoteDataSource closed