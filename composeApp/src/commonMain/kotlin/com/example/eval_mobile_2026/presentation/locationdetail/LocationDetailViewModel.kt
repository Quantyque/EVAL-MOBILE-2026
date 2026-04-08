package com.example.eval_mobile_2026.presentation.locationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eval_mobile_2026.domain.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    private val repository: LocationRepository,
    private val locationId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    init {
        loadLocation()
    }

    fun onAction(action: LocationDetailAction) {
        when (action) {
            is LocationDetailAction.Retry -> loadLocation()
        }
    }

    private fun loadLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.getLocation(locationId) }
                .onSuccess { location ->
                    _uiState.update { it.copy(isLoading = false, location = location) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}
