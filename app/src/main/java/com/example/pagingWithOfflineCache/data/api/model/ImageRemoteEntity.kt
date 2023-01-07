package com.example.pagingWithOfflineCache.data.api.model


import com.google.gson.annotations.SerializedName

data class ImageRemoteEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("user")
    val user: User)