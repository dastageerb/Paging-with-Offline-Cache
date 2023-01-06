package com.example.pagingWithOfflineCache.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val imageId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
