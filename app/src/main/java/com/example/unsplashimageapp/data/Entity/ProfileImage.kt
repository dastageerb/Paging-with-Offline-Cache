package com.example.unsplashimageapp.data.Entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImage(
    @Json(name = "large")
    val large: String?,
    @Json(name = "medium")
    val medium: String?,
    @Json(name = "small")
    val small: String?
)