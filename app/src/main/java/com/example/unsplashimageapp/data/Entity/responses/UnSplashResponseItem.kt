package com.example.unsplashimageapp.data.Entity.responses


import com.example.unsplashimageapp.data.Entity.utilEntities.Links
import com.example.unsplashimageapp.data.Entity.utilEntities.Sponsorship
import com.example.unsplashimageapp.data.Entity.utilEntities.Urls
import com.example.unsplashimageapp.data.Entity.utilEntities.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnSplashResponseItem(
    @Json(name = "alt_description")
    val altDescription: String?,
    @Json(name = "blur_hash")
    val blurHash: String?,
    @Json(name = "categories")
    val categories: List<Any>?,
    @Json(name = "color")
    val color: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any>?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean?,
    @Json(name = "likes")
    val likes: Int?,
    @Json(name = "links")
    val links: Links?,
    @Json(name = "promoted_at")
    val promotedAt: String?,
    @Json(name = "sponsorship")
    val sponsorship: Sponsorship?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "urls")
    val urls: Urls?,
    @Json(name = "user")
    val user: User?,
    @Json(name = "width")
    val width: Int?
)