package com.example.eval_mobile_2026.presentation.locationdetail

import com.example.eval_mobile_2026.domain.model.Location

data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val location: Location? = null,
    val error: String? = null
)
