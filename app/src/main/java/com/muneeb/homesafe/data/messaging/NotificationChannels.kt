package com.muneeb.homesafe.data.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.muneeb.homesafe.R

object NotificationChannels {
    const val ARRIVALS_CHANNEL_ID = "arrivals"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            ARRIVALS_CHANNEL_ID,
            context.getString(R.string.notification_channel_arrivals_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.notification_channel_arrivals_description)
        }
        context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }
}
