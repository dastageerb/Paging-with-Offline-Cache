package com.example.unsplashimageapp.data.Entity.responses


import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnSplashSearchResponse(
    @Json(name = "results")
    val results: List<UnSplashResponseItem>?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?
)