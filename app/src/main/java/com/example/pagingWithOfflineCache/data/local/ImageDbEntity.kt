package com.example.pagingWithOfflineCache.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pagingWithOfflineCache.utils.Constants

@Entity(tableName = Constants.IMAGES_TABLE)
data class ImageDbEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val likes:Int,
    val userName:String,
    val userProfileImage:String,
    val location:String?=null
    )