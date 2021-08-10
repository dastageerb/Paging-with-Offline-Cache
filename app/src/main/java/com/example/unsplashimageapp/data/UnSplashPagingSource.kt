package com.example.unsplashimageapp.data

import android.accounts.NetworkErrorException
import android.text.TextUtils
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashimageapp.data.Entity.UnSplashResponseItem
import com.example.unsplashimageapp.data.api.UnSplashApi
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class UnSplashPagingSource(private val api:UnSplashApi,private val query:String? = null) : PagingSource<Int,UnSplashResponseItem>()
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


        var response:Response<List<UnSplashResponseItem>>

        return try
        {

            // Same PagingSource is used for both api functions

            if(query.isNullOrEmpty())
            {
              response  = api.getPhotos(page,params.loadSize)
            }else
            {
                response  = api.searchPhotos(query,page,params.loadSize)
            }

            LoadResult.Page(
                data = response.body()!!,
                prevKey = if(page==1) null else page-1,
                nextKey = if(response.body().isNullOrEmpty()) null else page+1
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