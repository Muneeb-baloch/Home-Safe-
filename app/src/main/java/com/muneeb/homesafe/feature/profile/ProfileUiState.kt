package com.muneeb.homesafe.feature.profile

data class ProfileUiState(
    val name: String = "Alex Morgan",
    val email: String = "alex.morgan@example.com",
    val options: List<String> = listOf("Emergency Contacts", "Privacy Settings", "Notifications", "Help & Support")
)