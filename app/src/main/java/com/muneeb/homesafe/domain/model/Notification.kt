package com.muneeb.homesafe.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

enum class NotificationType {
    ARRIVAL,
    INVITE
}

data class HomeSafeNotification(
    val id: String = "",
    val outingId: String = "",
    val recipientId: String = "",
    val type: NotificationType = NotificationType.ARRIVAL,
    val arriverName: String = "",
    val arrivedAt: Date? = null,
    @ServerTimestamp val createdAt: Date? = null,
    val delivered: Boolean = false
)
