package com.muneeb.homesafe.domain.model

data class HomeLocation(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val radius: Int = 0,
    val address: String = ""
)
