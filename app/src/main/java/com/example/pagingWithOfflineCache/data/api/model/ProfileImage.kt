package com.example.pagingWithOfflineCache.data.api.model


import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("large")
    val url: String
)