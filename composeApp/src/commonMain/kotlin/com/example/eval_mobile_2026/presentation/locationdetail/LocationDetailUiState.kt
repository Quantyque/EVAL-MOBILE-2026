package com.example.eval_mobile_2026.presentation.locationdetail

import com.example.eval_mobile_2026.domain.model.Location

/**
 * Immutable state snapshot for the location detail screen.
 *
 * Two independent async operations are tracked separately:
 * - [isLoading] covers the main location fetch.
 * - [isLoadingResidents] covers the secondary character-name fetch, which runs
 *   concurrently after the location is available and degrades silently on failure.
 */
data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val location: Location? = null,
    val residentNames: List<String> = emptyList(),
    val isLoadingResidents: Boolean = false,
    val error: String? = null
)
