package com.example.pagingWithOfflineCache.di


import com.example.pagingWithOfflineCache.BuildConfig
import com.example.pagingWithOfflineCache.data.api.ImagesApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesIntercepton(): Interceptor =
        Interceptor { chain ->
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Accept-Version", "v1")
                        .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
                        .build()
                )
            }
        }

    @Singleton
    @Provides
    fun providesOkhttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    @Singleton
    @Provides
    fun provideUnSplashApi(retrofit: Retrofit): ImagesApi = retrofit.create(ImagesApi::class.java)

}