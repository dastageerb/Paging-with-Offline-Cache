package com.example.pagingWithOfflineCache.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingWithOfflineCache.utils.Constants.IMAGES_TABLE


@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<ImageDbEntity>)

    @Query("SELECT * FROM $IMAGES_TABLE")
    fun getAll(): PagingSource<Int, ImageDbEntity>

    @Query("DELETE FROM $IMAGES_TABLE")
    suspend fun deleteAll()
}