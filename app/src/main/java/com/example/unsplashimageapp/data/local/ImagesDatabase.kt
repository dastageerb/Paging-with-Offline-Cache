package com.example.unsplashimageapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplashimageapp.data.local.ImageDbEntity
import com.example.unsplashimageapp.data.local.ImagesDao
import com.example.unsplashimageapp.data.local.RemoteKey
import com.example.unsplashimageapp.data.local.RemoteKeyDao

@Database(version = 1, entities = [ImageDbEntity::class, RemoteKey::class])
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun getImagesDao(): ImagesDao
    abstract fun getKeysDao(): RemoteKeyDao
}