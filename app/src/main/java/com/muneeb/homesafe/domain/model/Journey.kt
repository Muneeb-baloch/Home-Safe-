package com.muneeb.homesafe.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Journey(
    val id: String = "",
    val outingId: String = "",
    val userId: String = "",
    val status: JourneyStatus = JourneyStatus.OUT,
    @ServerTimestamp val startedAt: Date? = null,
    val arrivedAt: Date? = null,
    val notified: Boolean = false
)
