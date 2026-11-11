package com.muneeb.homesafe.feature.outing

data class NotifiedCompanionUi(
    val uid: String,
    val name: String,
    val delivered: Boolean = false
)

data class ArrivalConfirmationUiState(
    val outingCode: String = "",
    val arrivedAtLabel: String = "",
    val companions: List<NotifiedCompanionUi> = emptyList()
)
