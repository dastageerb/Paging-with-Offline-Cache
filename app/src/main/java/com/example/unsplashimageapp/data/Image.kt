package com.example.unsplashimageapp.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "id")
    val id: String?,
    @Json(name = "urls")
    val urls: Urls?
)