package com.example.unsplashimageapp.data.api

import com.example.unsplashimageapp.data.Image
import retrofit2.Response
import retrofit2.http.*

interface ImagesApi
{
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<Image>>

}