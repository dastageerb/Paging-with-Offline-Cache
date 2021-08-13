package com.example.unsplashimageapp.data.remoteDataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unsplashimageapp.data.UnSplashAllPagingSource
import com.example.unsplashimageapp.data.UnSplashSearchPagingSource
import com.example.unsplashimageapp.data.api.UnSplashApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api:UnSplashApi)
{


    /** Home Fragment api call */
    fun getPhotos() = Pager(PagingConfig(20))
    {
        UnSplashAllPagingSource(api)
    }.flow

    fun searchPhotos(query:String) = Pager(PagingConfig(20))
    {
        UnSplashSearchPagingSource(api,query)
    }.flow


    /** Details Fragment api call */

    // getSinglePhoto  = photo Details
    suspend fun getSinglePhoto(id:String) = api.getSinglePhoto(id);



















} // RemoteDataSource closed