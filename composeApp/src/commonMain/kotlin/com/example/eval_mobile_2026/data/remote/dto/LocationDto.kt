package com.example.eval_mobile_2026.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Raw JSON representation of a location as returned by the Rick and Morty API.
 *
 * Note: [residents] contains full character URLs (e.g. ".../character/42").
 * The mapper extracts numeric IDs from these URLs when converting to the [com.example.eval_mobile_2026.domain.model.Location] business model.
 */
@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)
