package com.example.pagingWithOfflineCache.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagingWithOfflineCache.data.api.ImagesApi
import com.example.pagingWithOfflineCache.data.api.model.ImageRemoteEntity
import com.example.pagingWithOfflineCache.data.local.ImagesDatabase
import com.example.pagingWithOfflineCache.domain.ImageEntity
import com.example.pagingWithOfflineCache.data.local.model.RemoteKey
import com.example.pagingWithOfflineCache.utils.Constants.STARTING_PAGE_INDEX
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class ImagesRemoteMediator(
    private val api: ImagesApi,
    private val db: ImagesDatabase
) : RemoteMediator<Int, ImageEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        try {
            val response = api.getImages(page = page, perPage = state.config.pageSize)
            val isEndOfList = response.body()!!.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getImagesDao().deleteAll()
                    db.getKeysDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.body()!!.map {
                    RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.getKeysDao().insertAll(keys)
                db.getImagesDao().insertAll(mapNetWorkEntityIntoDatabaseEntity(response.body()!!))
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, ImageEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ImageEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getKeysDao().remoteKeysImageId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ImageEntity>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { image -> db.getKeysDao().remoteKeysImageId(image.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, ImageEntity>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { image -> db.getKeysDao().remoteKeysImageId(image.id) }
    }

    private fun mapNetWorkEntityIntoDatabaseEntity(body: List<ImageRemoteEntity>): List<ImageEntity> {
        val list = mutableListOf<ImageEntity>()
        body.forEach{ image ->
            list.add(
                ImageEntity(
                    id = image.id,
                    imageUrl = image.urls.imageUrl,
                    likes = image.likes,
                    location = image.user.location,
                    userName = image.user.username,
                    userProfileImage = image.user.profileImage.url
                )
            )
        }
        return list
    }

}