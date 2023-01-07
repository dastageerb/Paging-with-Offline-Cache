package com.example.pagingWithOfflineCache.data

import androidx.paging.*
import com.example.pagingWithOfflineCache.data.api.ImagesApi
import com.example.pagingWithOfflineCache.data.local.ImagesDatabase
import com.example.pagingWithOfflineCache.domain.ImageEntity
import com.example.pagingWithOfflineCache.data.mediator.ImagesRemoteMediator
import com.example.pagingWithOfflineCache.domain.ImagesRepository
import com.example.pagingWithOfflineCache.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class ImagesRepoImpl @Inject constructor(
    private val imageApi: ImagesApi,
    private val database: ImagesDatabase,
) : ImagesRepository {
    override suspend fun getAllImages(): Flow<PagingData<ImageEntity>> {
        val pagingSourceFactory = { database.getImagesDao().getAll() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE * 2),
            remoteMediator = ImagesRemoteMediator(imageApi,database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}