package com.muneeb.homesafe.feature.join

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class JoinConfirmationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userIdentityProvider: UserIdentityProvider,
    private val outingRepository: OutingRepository,
    private val journeyRepository: JourneyRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val outingId: String = checkNotNull(savedStateHandle["outingId"])

    private val _uiState = MutableStateFlow(JoinConfirmationUiState())
    val uiState: StateFlow<JoinConfirmationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val outing = outingRepository.getOuting(outingId) ?: return@launch
            val creatorName = userRepository.getUser(outing.createdBy)?.name.orEmpty()
            val memberNames = outing.members.map { uid -> userRepository.getUser(uid)?.name.orEmpty() }
            _uiState.value = _uiState.value.copy(
                invitedByName = creatorName,
                memberNames = memberNames,
                startedAtLabel = outing.createdAt?.let(::formatTime).orEmpty()
            )
        }
    }

    fun joinOuting() {
        if (_uiState.value.isJoining) return
        _uiState.value = _uiState.value.copy(isJoining = true, error = false)
        viewModelScope.launch {
            try {
                val uid = userIdentityProvider.currentUid()
                outingRepository.addMember(outingId, uid)
                journeyRepository.createJourney(outingId = outingId, userId = uid)
                _uiState.value = _uiState.value.copy(isJoining = false, joined = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isJoining = false, error = true)
            }
        }
    }

    private fun formatTime(date: Date): String =
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
}
