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
    private val coroutineScope: CoroutineScope
) {

    suspend fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = ImagesRemoteMediator(imageApi, database),
        pagingSourceFactory = { database.getImagesDao().getAll() }
    ).flow.cachedIn(coroutineScope)

//    suspend fun fillWithDummyCats(dummyCats: List<Image>) {
//        database.getImagesDao().deleteAll()
//        database.getImagesDao().insertAll(dummyCats)
//    }

//    suspend fun deleteDummyData() {
//        database.getCatDao().deleteAll()
//    }
}