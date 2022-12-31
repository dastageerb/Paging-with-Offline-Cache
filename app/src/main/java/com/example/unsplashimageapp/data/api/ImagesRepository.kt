package com.example.unsplashimageapp.data.api

import androidx.paging.*
import com.example.unsplashimageapp.data.local.ImagesDatabase
import com.example.unsplashimageapp.data.Image
import com.example.unsplashimageapp.data.local.ImageDbEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 30

@OptIn(ExperimentalPagingApi::class)
class ImagesRepository @Inject constructor(
    private val imageApi: ImagesApi,
    private val database: ImagesDatabase,
) {

    fun getAllImages(): Flow<PagingData<ImageDbEntity>> {
        val pagingSourceFactory = { database.getImagesDao().getAll() }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE, initialLoadSize =
                PAGE_SIZE * 2
            ),
            remoteMediator = ImagesRemoteMediator(imageApi,database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}