package com.example.unsplashimageapp.data.Entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subcategory(
    @Json(name = "pretty_slug")
    val prettySlug: String?,
    @Json(name = "slug")
    val slug: String?
)