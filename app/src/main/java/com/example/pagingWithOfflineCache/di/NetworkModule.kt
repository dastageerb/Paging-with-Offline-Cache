package com.example.pagingWithOfflineCache.di


import com.example.pagingWithOfflineCache.BuildConfig
import com.example.pagingWithOfflineCache.data.api.ImagesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule
{


    @Singleton
    @Provides
    fun providesIntercepton() : Interceptor =
        Interceptor { chain ->
            chain.run{
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Accept-Version","v1")
                        .addHeader("Authorization: Client-ID",BuildConfig.UNSPLASH_ACCESS_KEY)
                        .build()
                )
            }
        }

    @Singleton
    @Provides
    fun providesOkhttpClient(interceptor: Interceptor) : OkHttpClient
    = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitClient (okHttpClient: OkHttpClient) : Retrofit
    = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideUnSplashApi(retrofit: Retrofit) : ImagesApi = retrofit.create(ImagesApi::class.java)

}