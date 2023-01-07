package com.example.pagingWithOfflineCache.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 2, entities = [ImageDbEntity::class, RemoteKey::class],exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun getImagesDao(): ImagesDao
    abstract fun getKeysDao(): RemoteKeyDao
}