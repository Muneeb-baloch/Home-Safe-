package com.muneeb.homesafe

import android.app.Application
import com.muneeb.homesafe.data.messaging.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HomeSafeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.createChannels(this)
    }
}
