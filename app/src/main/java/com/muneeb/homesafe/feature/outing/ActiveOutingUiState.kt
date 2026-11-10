package com.muneeb.homesafe.feature.outing

import com.muneeb.homesafe.domain.model.JourneyStatus

data class OutingCompanionUi(
    val uid: String,
    val name: String
)

data class ActiveOutingUiState(
    val outingCode: String = "",
    val journeyStatus: JourneyStatus = JourneyStatus.OUT,
    val companions: List<OutingCompanionUi> = emptyList()
)
