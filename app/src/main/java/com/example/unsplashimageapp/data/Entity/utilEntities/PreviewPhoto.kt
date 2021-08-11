package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PreviewPhoto(
    @Json(name = "blur_hash")
    val blurHash: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "urls")
    val urls: Urls?
)