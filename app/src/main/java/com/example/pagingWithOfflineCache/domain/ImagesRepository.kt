package com.example.pagingWithOfflineCache.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun getAllImages(): Flow<PagingData<ImageEntity>>
}