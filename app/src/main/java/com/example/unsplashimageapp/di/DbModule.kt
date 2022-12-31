package com.c_od_e.pagination.di

import android.content.Context
import androidx.room.Room
import com.example.unsplashimageapp.data.local.ImagesDatabase
import com.example.unsplashimageapp.utils.Constants.IMAGES_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext appContext: Context
    ): ImagesDatabase {
        return Room.databaseBuilder(
            appContext, ImagesDatabase::class.java,
            IMAGES_DB
        ).build()
    }
}