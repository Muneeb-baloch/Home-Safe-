package com.muneeb.homesafe.feature.outing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.repository.JourneyRepository
import com.muneeb.homesafe.domain.repository.OutingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartOutingViewModel @Inject constructor(
    private val userIdentityProvider: UserIdentityProvider,
    private val outingRepository: OutingRepository,
    private val journeyRepository: JourneyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartOutingUiState())
    val uiState: StateFlow<StartOutingUiState> = _uiState.asStateFlow()

    fun startOuting() {
        if (_uiState.value.isStarting) return
        _uiState.value = _uiState.value.copy(isStarting = true, error = null)
        viewModelScope.launch {
            try {
                val uid = userIdentityProvider.currentUid()
                val outing = outingRepository.createOuting(createdBy = uid)
                journeyRepository.createJourney(outingId = outing.id, userId = uid)
                _uiState.value = _uiState.value.copy(isStarting = false, createdOutingId = outing.id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isStarting = false, error = e.message)
            }
        }
    }
}
