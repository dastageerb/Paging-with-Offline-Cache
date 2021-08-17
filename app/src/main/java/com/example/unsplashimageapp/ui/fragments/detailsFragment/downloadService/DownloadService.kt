package com.example.unsplashimageapp.ui.fragments.detailsFragment.downloadService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import com.example.unsplashimageapp.data.repository.Repository
import com.example.unsplashimageapp.utils.Constants.IMAGE_URL
import com.example.unsplashimageapp.utils.Constants.NOTIFICATION_ID
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.DownloadStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*
import javax.inject.Inject
@AndroidEntryPoint
class DownloadService() : Service()
{

    @Inject
    lateinit var  repository: Repository

    @Inject
    lateinit var notificationChannel: NotificationChannel
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var baseNotification: NotificationCompat.Builder

    companion object
    {

        val downloadingStatus : MutableStateFlow<DownloadStatus> = MutableStateFlow(DownloadStatus.Null)

    }

    private val coroutineJob:Job = Job()
    private val coroutineScope =  CoroutineScope(Dispatchers.IO+coroutineJob)


    override fun onCreate()
    {
        super.onCreate()
        Timber.tag(TAG).d("Service started")
    } // onCreate closed

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        intent?.let()
        {
            val imageUrl = intent.getStringExtra(IMAGE_URL).toString()
            coroutineScope.launch ()
            {
                downloadPhoto(imageUrl)
            } // coroutine scope closed
        } // let closed
        return START_NOT_STICKY
    } // on Start command closed


    private suspend fun downloadPhoto(imageUrl:String)
    {
        try
        {
            createNotification()

            downloadingStatus.value = DownloadStatus.Loading

            val response = repository.remote.downloadPhoto(imageUrl)
            saveImageToFile(response)
            Timber.tag(TAG).d("response : "+response.byteStream())
        }catch (e:Exception)
        {

            Timber.tag(TAG).d("error : "+e)
            Timber.tag(TAG).d("error m : "+e.message)
            Timber.tag(TAG).d("error cause : "+e.cause)
            downloadingStatus.value = DownloadStatus.Error

        } // catch closed
    } // downloadPhoto closed

    @WorkerThread
    private fun saveImageToFile(body: ResponseBody)
    {
        try
        {
            var count: Int
            updateNotification("Downloading File")
        val data = ByteArray(1024 * 4)
       // val fileSize = body.contentLength()

        val inputStream: InputStream = BufferedInputStream(body.byteStream(), 1024 * 8)


        val outputFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis().toString() + ".jpg")
        val outputStream: OutputStream = FileOutputStream(outputFile)

        while (inputStream.read(data).also { count = it } != -1)
        {
            outputStream.write(data, 0, count)
        }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            updateNotification("Image Downloaded")
            stopForeground(true)
            downloadingStatus.value = DownloadStatus.Success

        }catch (e:IOException)
        {
            Timber.tag(TAG).d("${e.message}")
            downloadingStatus.value = DownloadStatus.Error
        }

    } // saveImage to File



   private fun createNotification()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            notificationManager.createNotificationChannel(notificationChannel)
        }
        startForeground(NOTIFICATION_ID,baseNotification.build())

    } // create notification closed

    private fun updateNotification(msg:String)
    {
        baseNotification.setContentText(msg)
        notificationManager.notify(NOTIFICATION_ID,baseNotification.build())
    } // updateNotification


    override fun onBind(intent: Intent?): IBinder?
    {
        return null
    }


    override fun onDestroy()
    {
        super.onDestroy()
        coroutineJob.cancel()
    }

} // Download Service closed