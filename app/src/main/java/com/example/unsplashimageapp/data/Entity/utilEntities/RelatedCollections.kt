package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedCollections(
    @Json(name = "results")
    val results: List<Result>?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "type")
    val type: String?
)