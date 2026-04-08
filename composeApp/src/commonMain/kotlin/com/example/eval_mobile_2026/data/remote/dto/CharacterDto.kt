package com.example.eval_mobile_2026.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Partial DTO for a character from the Rick and Morty API.
 * Only [id] and [name] are used; the remaining fields returned by the API are ignored.
 */
@Serializable
data class CharacterDto(
    val id: Int,
    val name: String
)
