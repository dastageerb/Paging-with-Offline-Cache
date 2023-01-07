package com.example.pagingWithOfflineCache.data.api.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("location")
    val location: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @SerializedName("username")
    val username: String
)