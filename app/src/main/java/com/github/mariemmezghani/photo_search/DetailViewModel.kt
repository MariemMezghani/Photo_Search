package com.github.mariemmezghani.photo_search

import android.app.Application
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mariemmezghani.photo_search.database.PhotoDatabase
import com.github.mariemmezghani.photo_search.fragments.DetailFragment
import com.github.mariemmezghani.photo_search.model.Photo
import com.github.mariemmezghani.photo_search.utils.sendNotification
import kotlinx.coroutines.launch

class DetailViewModel(application: Application, photo: Photo) : AndroidViewModel(application) {
    //define an instance of DownloadManager
    val downloadManager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val database = PhotoDatabase.getInstance(application).photoDAO

    // notificationManager instance
    val notificationManager = getSystemService(
        application,
        NotificationManager::class.java
    ) as NotificationManager
    private val _selectedPhoto = MutableLiveData<Photo>()
    val selectedPhoto: LiveData<Photo>
        get() = _selectedPhoto

    // BroadcastReciever instance
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // show the user a notification when the download is complete
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == DetailFragment.downloadId) { // means download is complete
                if (context != null) {
                    notificationManager.sendNotification("file download is complete", context)
                }


            }
        }
    }

    init {
        _selectedPhoto.value = photo
    }

    fun download(photo: Photo) {
        //generate a unique filename
        val filename = "${System.currentTimeMillis()}.jpg"
        val request = DownloadManager.Request(Uri.parse(photo.url))
            .setTitle(filename)
            .setDescription(photo.title)
            // show notification bar when downloading
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverRoaming(true)
            // download is allowed on mobile network
            .setAllowedOverRoaming(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, filename)


        //enqueue function puts the download request in the queue and returns the rquest id
        DetailFragment.downloadId = downloadManager.enqueue(request)
    }

    // create notificationChannel
    fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Download"
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    // add favorites to database
    private suspend fun insertFavorite(photo: Photo) {
        database.insert(photo)

    }

    fun onAddFavorite(photo: Photo) {
        viewModelScope.launch { insertFavorite(photo) }

    }

    // remove favorites from database
    private suspend fun removeFavorite(photo: Photo) {
        database.delete(photo)

    }

    fun onDeleteFavorite(photo: Photo) {
        viewModelScope.launch { removeFavorite(photo) }

    }


}