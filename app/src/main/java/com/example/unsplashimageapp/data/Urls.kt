package com.example.unsplashimageapp.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "regular")
    val regular: String?,
    @Json(name = "thumb")
    val thumb: String?
)