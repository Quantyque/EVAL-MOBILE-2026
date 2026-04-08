package com.example.eval_mobile_2026.domain.model

/**
 * Business model representing a location from the Rick and Morty universe.
 *
 * This model belongs to the Domain layer and has no knowledge of the remote API,
 * DTOs, or persistence mechanisms. It is the single source of truth consumed
 * by the Presentation layer and produced by the Data layer via mapping.
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    /** IDs of the characters present at this location, extracted from the resident URLs. */
    val residents: List<Int>,
    val url: String,
    val created: String
)
