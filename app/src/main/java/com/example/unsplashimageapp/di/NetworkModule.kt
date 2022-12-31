package com.example.unsplashimageapp.di


import com.example.unsplashimageapp.data.api.ImagesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        .baseUrl("")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideUnSplashApi(retrofit: Retrofit) : ImagesApi = retrofit.create(ImagesApi::class.java)

}