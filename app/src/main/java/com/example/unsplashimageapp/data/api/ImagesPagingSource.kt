package com.example.unsplashimageapp.data.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashimageapp.data.Image
import com.example.unsplashimageapp.data.local.ImageDbEntity
import com.example.unsplashimageapp.utils.Constants.STARTING_PAGE_INDEX
import com.example.unsplashimageapp.utils.Mappers.mapNetWorkEntityIntoDatabaseEntity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class ImagesPagingSource(private val service: ImagesApi) : PagingSource<Int, ImageDbEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDbEntity> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = mapNetWorkEntityIntoDatabaseEntity(service.getImages(page = page, perPage = params.loadSize).body()!!)

            LoadResult.Page(data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageDbEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}