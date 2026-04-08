package com.example.eval_mobile_2026.presentation.locationlist

import com.example.eval_mobile_2026.domain.model.Location

/**
 * Immutable state snapshot for the location list screen.
 *
 * State transitions follow the UDF pattern:
 * - Initial load  : [isLoading] = true, [locations] empty
 * - Data ready    : [isLoading] = false, [locations] populated, [totalCount] set
 * - Pagination    : [isLoading] = true while next page loads, [locations] kept
 * - Error         : [isLoading] = false, [error] non-null
 */
data class LocationListUiState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val error: String? = null,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
    /** Total number of locations as reported by the API. 0 until the first remote response. */
    val totalCount: Int = 0
)
