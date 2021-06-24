package com.github.mariemmezghani.photo_search

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mariemmezghani.photo_search.databinding.FragmentDetailBinding
import com.github.mariemmezghani.photo_search.model.Photo
import com.github.mariemmezghani.photo_search.utils.sendNotification


class DetailFragment : Fragment() {

    private var downloadId: Long = 0
    private lateinit var notificationManager: NotificationManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val photo = DetailFragmentArgs.fromBundle(requireArguments()).photo

        val factory = DetailViewModelFactory(photo = photo)
        val detailViewModel =
                ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        binding.detailViewModel = detailViewModel

        // notificationManager instance
        notificationManager = context?.let { getSystemService(
                    it,
                NotificationManager::class.java
        )
        } as NotificationManager
        createChannel(CHANNEL_ID, "channel")


        // register the BroadcastReciever
        context?.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        binding.button.setOnClickListener {
            download(photo)
        }


        return binding.root
    }
    // BroadcastReciever instance
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // show the user a notification when the download is complete
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) { // means download is complete
                if (context != null) {
                    notificationManager.sendNotification("file download is complete", context)
                }


            }
        }
    }

    private fun download(photo: Photo) {
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

        //define an instance of DownloadManager
        val downloadManager = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        //enqueue function puts the download request in the queue and returns the rquest id
        downloadId = downloadManager.enqueue(request)
    }
    // create notificationChannel

    private fun createChannel(channelId: String, channelName: String) {
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
    companion object{
        const val CHANNEL_ID = "channelId"

    }
}