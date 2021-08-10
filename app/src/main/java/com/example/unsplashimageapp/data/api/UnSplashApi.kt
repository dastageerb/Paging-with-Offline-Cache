package com.example.unsplashimageapp.data.api

import com.example.unsplashimageapp.data.Entity.UnSplashResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashApi
{

    @GET("photos")
    suspend fun getPhotos(
        @Query("page")page:Int,
        @Query("per_page")perPage:Int
    ) : Response<List<UnSplashResponseItem>>


    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query")query: String,
        @Query("page")page:Int,
        @Query("per_page")perPage:Int
    ) : Response<List<UnSplashResponseItem>>


}