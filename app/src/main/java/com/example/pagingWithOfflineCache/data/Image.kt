package com.example.pagingWithOfflineCache.data


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val urls: Urls,
)