package com.muneeb.homesafe.feature.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.domain.repository.OutingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterOutingCodeViewModel @Inject constructor(
    private val outingRepository: OutingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnterOutingCodeUiState())
    val uiState: StateFlow<EnterOutingCodeUiState> = _uiState.asStateFlow()

    fun onCodeChange(code: String) {
        _uiState.value = _uiState.value.copy(code = code, showError = false)
    }

    fun findOuting() {
        val code = _uiState.value.code.uppercase()
        if (code.length != 4 || _uiState.value.isSearching) return
        _uiState.value = _uiState.value.copy(isSearching = true, showError = false)
        viewModelScope.launch {
            try {
                val outing = outingRepository.findActiveOutingByCode(code)
                if (outing != null) {
                    _uiState.value = _uiState.value.copy(isSearching = false, foundOutingId = outing.id)
                } else {
                    _uiState.value = _uiState.value.copy(isSearching = false, showError = true)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSearching = false, showError = true)
            }
        }
    }
}
