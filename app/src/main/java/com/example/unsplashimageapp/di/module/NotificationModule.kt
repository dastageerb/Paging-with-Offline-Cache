package com.example.unsplashimageapp.di.module

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.example.unsplashimageapp.utils.Constants.NOTIFICATION_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule
{



    @Singleton
    @Provides
    fun providesNotificationChannel():NotificationChannel
    =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH)
        } else
        {
            TODO("VERSION.SDK_INT < O")
        }

    @Provides
    @Singleton
    fun providesNotificationManager(@ApplicationContext context:Context):NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Singleton
    fun providesNotificationBuilder(@ApplicationContext context: Context):NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_place_holder_24)
            .setOngoing(true)
            .setContentTitle("Downloading Started")
            .setContentText("Waiting for response")
            .setProgress(50,25,true)


}