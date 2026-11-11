package com.muneeb.homesafe.data.messaging

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.muneeb.homesafe.R
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeSafeMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var userIdentityProvider: UserIdentityProvider

    @Inject
    lateinit var userRepository: UserRepository

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            val uid = userIdentityProvider.currentUid()
            userRepository.addFcmToken(uid, token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val name = message.data["name"] ?: return
        val status = message.data["status"] ?: return
        if (status != "ARRIVED") return
        val arrivedAt = message.data["arrivedAt"]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val title = getString(R.string.notification_arrived_title_format, name)
        val body = if (!arrivedAt.isNullOrEmpty()) {
            getString(R.string.notification_arrived_body_format, arrivedAt)
        } else {
            getString(R.string.notification_arrived_body_generic)
        }

        val notification = NotificationCompat.Builder(this, NotificationChannels.ARRIVALS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .build()

        getSystemService(NotificationManager::class.java)?.notify(System.currentTimeMillis().toInt(), notification)
    }
}
