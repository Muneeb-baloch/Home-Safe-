package com.muneeb.homesafe.domain.repository

import com.muneeb.homesafe.domain.model.HomeSafeNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun observeNotificationsForOuting(outingId: String): Flow<List<HomeSafeNotification>>
    fun observeNotificationsForRecipient(recipientId: String): Flow<List<HomeSafeNotification>>
}
