package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exif(
    @Json(name = "aperture")
    val aperture: String?,
    @Json(name = "exposure_time")
    val exposureTime: Any?,
    @Json(name = "focal_length")
    val focalLength: String?,
    @Json(name = "iso")
    val iso: Any?,
    @Json(name = "make")
    val make: String?,
    @Json(name = "model")
    val model: String?
)