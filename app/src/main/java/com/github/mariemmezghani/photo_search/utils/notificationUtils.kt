package com.github.mariemmezghani.photo_search.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.mariemmezghani.photo_search.fragments.DetailFragment
import com.github.mariemmezghani.photo_search.R

// Notification ID.
private val NOTIFICATION_ID = 0

//extension function to NotificationManager to send notifications
fun NotificationManager.sendNotification(message: String, appContext: Context) {

    val builder = NotificationCompat.Builder(appContext, DetailFragment.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Download Complete")
        .setContentText(message)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}