package com.example.unsplashimageapp.data.api

import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.data.Entity.responses.UnSplashSearchResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

//    @GET("data/{version}/")
//    fun getWeatherReport1(@Path("version") version: String?): Call<Weather?>?

    @GET("/photos/{id}")
    suspend fun getSinglePhoto(@Path("id")id:String) : Response<PhotoResponse>

//    @GET("photos/{id}")
//    suspend fun getByIdSuspend(@Path("id") id: String): Response<PhotoResponse>

}