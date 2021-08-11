package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "city")
    val city: Any?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "position")
    val position: Position?,
    @Json(name = "title")
    val title: String?
)