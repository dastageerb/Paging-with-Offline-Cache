package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Social(
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "paypal_email")
    val paypalEmail: Any?,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "twitter_username")
    val twitterUsername: String?
)