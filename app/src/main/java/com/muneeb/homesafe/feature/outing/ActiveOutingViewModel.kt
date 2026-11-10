package com.muneeb.homesafe.feature.outing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.repository.JourneyRepository
import com.muneeb.homesafe.domain.repository.OutingRepository
import com.muneeb.homesafe.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveOutingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userIdentityProvider: UserIdentityProvider,
    private val outingRepository: OutingRepository,
    private val journeyRepository: JourneyRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val outingId: String = checkNotNull(savedStateHandle["outingId"])

    private val _uiState = MutableStateFlow(ActiveOutingUiState())
    val uiState: StateFlow<ActiveOutingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            outingRepository.observeOuting(outingId).collect { outing ->
                _uiState.value = _uiState.value.copy(outingCode = outing?.code.orEmpty())
            }
        }
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            journeyRepository.observeJourneysForOuting(outingId).collect { journeys ->
                val myStatus = journeys.firstOrNull { it.userId == uid }?.status
                    ?: _uiState.value.journeyStatus
                val companionUids = journeys.map { it.userId }.filter { it != uid }.distinct()
                val companions = companionUids.map { companionUid ->
                    val name = userRepository.getUser(companionUid)?.name.orEmpty()
                    OutingCompanionUi(uid = companionUid, name = name)
                }
                _uiState.value = _uiState.value.copy(journeyStatus = myStatus, companions = companions)
            }
        }
    }
}
