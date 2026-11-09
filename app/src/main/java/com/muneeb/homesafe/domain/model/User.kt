package com.muneeb.homesafe.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val homeLocation: HomeLocation = HomeLocation(),
    val fcmTokens: List<String> = emptyList()
)
