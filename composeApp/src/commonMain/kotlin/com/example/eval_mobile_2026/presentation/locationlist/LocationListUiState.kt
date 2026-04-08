package com.example.eval_mobile_2026.presentation.locationlist

import com.example.eval_mobile_2026.domain.model.Location

data class LocationListUiState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val error: String? = null,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1
)
