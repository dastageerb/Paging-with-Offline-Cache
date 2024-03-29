package com.example.pagingWithOfflineCache.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingWithOfflineCache.data.local.model.RemoteKey


@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE imageId = :id")
    suspend fun remoteKeysImageId(id: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}

