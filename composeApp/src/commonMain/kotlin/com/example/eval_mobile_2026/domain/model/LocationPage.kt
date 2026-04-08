package com.example.eval_mobile_2026.domain.model

data class LocationPage(
    val locations: List<Location>,
    val hasNextPage: Boolean,
    val nextPageNumber: Int?
)
