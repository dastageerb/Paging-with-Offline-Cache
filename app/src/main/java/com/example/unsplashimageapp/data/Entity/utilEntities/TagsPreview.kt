package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TagsPreview(
    @Json(name = "title")
    val title: String?,
    @Json(name = "type")
    val type: String?
)