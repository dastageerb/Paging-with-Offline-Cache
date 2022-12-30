package com.example.unsplashimageapp.data.api

import com.example.unsplashimageapp.data.Image
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UnSplashApi
{
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<Image>>

}