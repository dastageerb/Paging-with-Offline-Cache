package com.example.unsplashimageapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unsplashimageapp.utils.Constants

@Entity(tableName = Constants.IMAGES_TABLE)
data class ImageDbEntity(@PrimaryKey val id: String, val url: String)