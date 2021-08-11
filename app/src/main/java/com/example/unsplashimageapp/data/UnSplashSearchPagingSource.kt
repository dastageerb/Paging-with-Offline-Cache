package com.example.unsplashimageapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.data.api.UnSplashApi
import retrofit2.HttpException
import java.io.IOException

class UnSplashSearchPagingSource(private val api:UnSplashApi, private val query:String? = null) : PagingSource<Int, UnSplashResponseItem>()
{

    override fun getRefreshKey(state: PagingState<Int, UnSplashResponseItem>): Int?
    {
        return state.anchorPosition?.let()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashResponseItem>
    {
        val page = params.key ?: 1



        return try
        {

            // Same PagingSource is used for both api functions

              val  response = api.searchPhotos(query.toString(),page,params.loadSize)

            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = if(page==1) null else page-1,
                nextKey = if(response.body()?.results.isNullOrEmpty()) null else page+1
            )

        }catch (e: IOException)
        {
            LoadResult.Error(e)
        } catch (e:HttpException)
        {
            LoadResult.Error(e)
        }










    } // load closed


} // UnSplashPagingSource closed