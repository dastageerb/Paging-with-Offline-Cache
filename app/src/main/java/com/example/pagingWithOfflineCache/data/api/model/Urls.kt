package com.example.pagingWithOfflineCache.data.api.model


import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("regular")
    val imageUrl: String
)