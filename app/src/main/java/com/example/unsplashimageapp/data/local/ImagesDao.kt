package com.c_od_e.pagination.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c_od_e.pagination.model.Cat
import com.example.unsplashimageapp.utils.Constants

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<Cat>)

    @Query("SELECT * FROM ${Constants.IMAGES_TABLE}")
    fun getAll(): PagingSource<Int, Cat>

    @Query("DELETE FROM cats")
    suspend fun deleteAll()
}