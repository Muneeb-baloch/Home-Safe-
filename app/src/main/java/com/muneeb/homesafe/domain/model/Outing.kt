package com.muneeb.homesafe.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Outing(
    val id: String = "",
    val code: String = "",
    val createdBy: String = "",
    val members: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Date? = null,
    val expiresAt: Date? = null,
    val status: OutingStatus = OutingStatus.ACTIVE
)
