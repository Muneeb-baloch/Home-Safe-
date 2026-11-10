package com.muneeb.homesafe.feature.outing

import com.muneeb.homesafe.domain.model.JourneyStatus

enum class CompanionSubtitleKind { STARTED, JOINED, ARRIVED }

data class JoinedCompanionUi(
    val uid: String,
    val name: String,
    val subtitleKind: CompanionSubtitleKind,
    val subtitleTime: String,
    val arrived: Boolean
)

data class JoinedOutingUiState(
    val journeyStatus: JourneyStatus = JourneyStatus.OUT,
    val companions: List<JoinedCompanionUi> = emptyList()
)
