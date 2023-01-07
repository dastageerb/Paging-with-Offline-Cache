package com.example.pagingWithOfflineCache.di


import com.example.pagingWithOfflineCache.data.ImagesRepoImpl
import com.example.pagingWithOfflineCache.data.api.ImagesApi
import com.example.pagingWithOfflineCache.data.local.ImagesDatabase
import com.example.pagingWithOfflineCache.domain.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideImagesRepository(imagesApi: ImagesApi,imagesDatabase: ImagesDatabase):ImagesRepository
    = ImagesRepoImpl(imagesApi,imagesDatabase)

}