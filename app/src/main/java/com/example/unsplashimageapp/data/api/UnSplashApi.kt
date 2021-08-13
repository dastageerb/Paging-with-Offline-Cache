package com.example.unsplashimageapp.data.api

import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.data.Entity.responses.UnSplashSearchResponse
import retrofit2.Response
import retrofit2.http.*

interface UnSplashApi
{

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<UnSplashResponseItem>>


    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<UnSplashSearchResponse>


    // // getSinglePhoto  = photo Details
    @GET("/photos/{id}")
    suspend fun getSinglePhoto(@Path("id")id:String) : Response<PhotoResponse>





}