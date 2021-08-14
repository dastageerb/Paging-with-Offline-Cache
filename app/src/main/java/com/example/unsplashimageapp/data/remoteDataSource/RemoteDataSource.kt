package com.example.unsplashimageapp.data.remoteDataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unsplashimageapp.data.UnSplashAllPagingSource
import com.example.unsplashimageapp.data.UnSplashSearchPagingSource
import com.example.unsplashimageapp.data.api.UnSplashApi
import com.example.unsplashimageapp.utils.ExtensionFunction.url
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
    suspend fun getPhotoDetails(id:String) = api.getPhotoDetails(id);

    // getSinglePhoto  = photo Details
    suspend fun downloadPhoto(url:String) = api.downloadPhoto(url);


















} // RemoteDataSource closed