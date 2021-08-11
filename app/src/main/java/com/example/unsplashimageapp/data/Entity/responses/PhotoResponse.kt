package com.example.unsplashimageapp.data.Entity.responses


import com.example.unsplashimageapp.data.Entity.utilEntities.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoResponse(
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
    @Json(name = "downloads")
    val downloads: Int?,
    @Json(name = "exif")
    val exif: Exif?,
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
    @Json(name = "location")
    val location: Location?,
    @Json(name = "meta")
    val meta: Meta?,
    @Json(name = "promoted_at")
    val promotedAt: Any?,
    @Json(name = "related_collections")
    val relatedCollections: RelatedCollections?,
    @Json(name = "sponsorship")
    val sponsorship: Any?,
    @Json(name = "tags")
    val tags: List<Tag>?,
    @Json(name = "tags_preview")
    val tagsPreview: List<TagsPreview>?,
    @Json(name = "topics")
    val topics: List<Any>?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "urls")
    val urls: Urls?,
    @Json(name = "user")
    val user: User?,
    @Json(name = "views")
    val views: Int?,
    @Json(name = "width")
    val width: Int?
)