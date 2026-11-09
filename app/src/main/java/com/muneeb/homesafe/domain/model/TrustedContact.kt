package com.muneeb.homesafe.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

enum class TrustedContactStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}

data class TrustedContact(
    val contactUid: String = "",
    val status: TrustedContactStatus = TrustedContactStatus.PENDING,
    val notifyOnArrival: Boolean = true,
    @ServerTimestamp val createdAt: Date? = null
)
