package com.muneeb.homesafe.feature.outing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.data.remote.ArrivalNotifyApi
import com.muneeb.homesafe.domain.repository.JourneyRepository
import com.muneeb.homesafe.domain.repository.NotificationRepository
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
class ArrivalConfirmationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userIdentityProvider: UserIdentityProvider,
    private val outingRepository: OutingRepository,
    private val journeyRepository: JourneyRepository,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
    private val arrivalNotifyApi: ArrivalNotifyApi
) : ViewModel() {

    private val outingId: String = checkNotNull(savedStateHandle["outingId"])

    private val _uiState = MutableStateFlow(ArrivalConfirmationUiState())
    val uiState: StateFlow<ArrivalConfirmationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            val outing = outingRepository.getOuting(outingId) ?: return@launch
            val journey = journeyRepository.findJourney(outingId, uid) ?: return@launch

            val companions = outing.members.filter { it != uid }.map { companionUid ->
                NotifiedCompanionUi(uid = companionUid, name = userRepository.getUser(companionUid)?.name.orEmpty())
            }
            _uiState.value = _uiState.value.copy(
                outingCode = outing.code,
                arrivedAtLabel = formatTime(Date()),
                companions = companions
            )

            journeyRepository.markArrived(journey.id)
            arrivalNotifyApi.notifyArrival(journey.id)

            launch {
                notificationRepository.observeNotificationsForOuting(outingId).collect { notifications ->
                    val deliveredUids = notifications.filter { it.delivered }.map { it.recipientId }.toSet()
                    _uiState.value = _uiState.value.copy(
                        companions = _uiState.value.companions.map { companion ->
                            companion.copy(delivered = companion.uid in deliveredUids)
                        }
                    )
                }
            }
        }
    }

    private fun formatTime(date: Date): String =
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
}
