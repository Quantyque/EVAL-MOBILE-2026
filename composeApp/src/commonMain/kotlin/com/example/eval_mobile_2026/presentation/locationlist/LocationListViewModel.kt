package com.example.eval_mobile_2026.presentation.locationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eval_mobile_2026.domain.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListUiState())
    val uiState: StateFlow<LocationListUiState> = _uiState.asStateFlow()

    init {
        loadPage(page = 1)
    }

    fun onAction(action: LocationListAction) {
        when (action) {
            is LocationListAction.LoadNextPage -> {
                val state = _uiState.value
                if (!state.isLoading && state.hasNextPage) {
                    loadPage(state.currentPage + 1)
                }
            }
            is LocationListAction.Retry -> loadPage(_uiState.value.currentPage)
            is LocationListAction.SelectLocation -> Unit // handled by the composable via callback
        }
    }

    private fun loadPage(page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.getLocations(page) }
                .onSuccess { locationPage ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            locations = (if (page == 1) locationPage.locations
                                        else state.locations + locationPage.locations)
                                        .sortedBy { it.name },
                            hasNextPage = locationPage.hasNextPage,
                            currentPage = page,
                            totalCount = if (locationPage.totalCount > 0) locationPage.totalCount
                                         else state.totalCount
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}
