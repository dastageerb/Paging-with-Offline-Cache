package com.example.unsplashimageapp.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UnSplashApi
{

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<Any>>



    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<Any>


    // // getSinglePhoto  = photo Details
    @GET("/photos/{id}")
    suspend fun getPhotoDetails(@Path("id")id:String) : Response<Any>


    @GET
    @Streaming
    suspend fun downloadPhoto(@Url url: String) : ResponseBody





}