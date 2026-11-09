package com.muneeb.homesafe.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userIdentityProvider: UserIdentityProvider,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            userRepository.observeUser(uid).collect { user ->
                if (user != null) {
                    _uiState.value = _uiState.value.copy(
                        name = user.name,
                        email = user.email
                    )
                }
            }
        }
    }

    fun onArrivalAlertsChange(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(arrivalAlertsEnabled = enabled)
    }

    fun onOutingInvitesChange(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(outingInvitesEnabled = enabled)
    }

    fun saveProfile(name: String) {
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            userRepository.updateName(uid, name)
        }
    }
}
