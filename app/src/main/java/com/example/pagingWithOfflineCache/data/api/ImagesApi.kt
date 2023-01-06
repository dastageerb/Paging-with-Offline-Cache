package com.example.pagingWithOfflineCache.data.api

import com.example.pagingWithOfflineCache.data.Image
import retrofit2.Response
import retrofit2.http.*

interface ImagesApi
{
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<ArrayList<Image>>

}