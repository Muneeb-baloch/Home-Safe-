package com.muneeb.homesafe.feature.profile

data class ProfileUiState(
    val name: String = "Muneeb",
    val email: String = "rehanbaloch38901@gmail.com",
    val phone: String = "+92 321 5550123",
    val homeAddress: String = "Block 4, Gulshan-e-Iqbal, Karachi",
    val homeRadiusLabel: String = "150m",
    val arrivalAlertsEnabled: Boolean = true,
    val outingInvitesEnabled: Boolean = true
)
