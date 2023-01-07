package com.example.pagingWithOfflineCache.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pagingWithOfflineCache.data.local.dao.ImagesDao
import com.example.pagingWithOfflineCache.data.local.dao.RemoteKeyDao
import com.example.pagingWithOfflineCache.data.local.model.RemoteKey
import com.example.pagingWithOfflineCache.domain.ImageEntity

@Database(version = 2, entities = [ImageEntity::class, RemoteKey::class],exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun getImagesDao(): ImagesDao
    abstract fun getKeysDao(): RemoteKeyDao
}