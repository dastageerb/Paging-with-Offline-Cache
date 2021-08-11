package com.example.unsplashimageapp.data.Entity.utilEntities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto?,
    @Json(name = "curated")
    val curated: Boolean?,
    @Json(name = "description")
    val description: Any?,
    @Json(name = "featured")
    val featured: Boolean?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String?,
    @Json(name = "links")
    val links: Links?,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto>?,
    @Json(name = "private")
    val `private`: Boolean?,
    @Json(name = "published_at")
    val publishedAt: String?,
    @Json(name = "share_key")
    val shareKey: String?,
    @Json(name = "tags")
    val tags: List<Tag>?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "total_photos")
    val totalPhotos: Int?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "user")
    val user: User?
)