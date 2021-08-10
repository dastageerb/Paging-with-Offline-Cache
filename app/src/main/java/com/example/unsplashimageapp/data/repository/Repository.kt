package com.example.unsplashimageapp.data.repository

import com.example.unsplashimageapp.data.remoteDataSource.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(remoteDataSource: RemoteDataSource)
{

    val remote = remoteDataSource

}