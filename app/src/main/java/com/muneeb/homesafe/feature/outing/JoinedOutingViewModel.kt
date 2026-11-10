package com.muneeb.homesafe.feature.outing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.model.Journey
import com.muneeb.homesafe.domain.model.JourneyStatus
import com.muneeb.homesafe.domain.repository.JourneyRepository
import com.muneeb.homesafe.domain.repository.OutingRepository
import com.muneeb.homesafe.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class JoinedOutingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userIdentityProvider: UserIdentityProvider,
    private val outingRepository: OutingRepository,
    private val journeyRepository: JourneyRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val outingId: String = checkNotNull(savedStateHandle["outingId"])

    private val _uiState = MutableStateFlow(JoinedOutingUiState())
    val uiState: StateFlow<JoinedOutingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            val outing = outingRepository.getOuting(outingId)
            val createdBy = outing?.createdBy.orEmpty()
            journeyRepository.observeJourneysForOuting(outingId).collect { journeys ->
                val myStatus = journeys.firstOrNull { it.userId == uid }?.status
                    ?: _uiState.value.journeyStatus
                val companions = journeys
                    .filter { it.userId != uid }
                    .map { journey -> toCompanionUi(journey, createdBy) }
                _uiState.value = _uiState.value.copy(journeyStatus = myStatus, companions = companions)
            }
        }
    }

    private suspend fun toCompanionUi(journey: Journey, createdBy: String): JoinedCompanionUi {
        val name = userRepository.getUser(journey.userId)?.name.orEmpty()
        val arrived = journey.status == JourneyStatus.ARRIVED || journey.status == JourneyStatus.COMPLETED
        val (kind, time) = when {
            arrived -> CompanionSubtitleKind.ARRIVED to journey.arrivedAt?.let(::formatTime).orEmpty()
            journey.userId == createdBy -> CompanionSubtitleKind.STARTED to ""
            else -> CompanionSubtitleKind.JOINED to journey.startedAt?.let(::formatTime).orEmpty()
        }
        return JoinedCompanionUi(uid = journey.userId, name = name, subtitleKind = kind, subtitleTime = time, arrived = arrived)
    }

    private fun formatTime(date: Date): String =
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
}
