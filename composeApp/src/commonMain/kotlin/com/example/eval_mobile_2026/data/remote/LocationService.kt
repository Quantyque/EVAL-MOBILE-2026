package com.example.eval_mobile_2026.data.remote

import com.example.eval_mobile_2026.data.remote.dto.CharacterDto
import com.example.eval_mobile_2026.data.remote.dto.LocationDto
import com.example.eval_mobile_2026.data.remote.dto.LocationPageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://rickandmortyapi.com/api"

/**
 * Ktor-based HTTP client for the Rick and Morty REST API.
 *
 * Responsible for executing raw network calls and returning deserialized DTOs.
 * No business logic is applied here; mapping to domain models is handled separately
 * by the mapper layer.
 */
class LocationService(private val client: HttpClient) {

    /** Fetches a paginated list of locations for the given [page] number (1-indexed). */
    suspend fun fetchLocations(page: Int): LocationPageDto =
        client.get("$BASE_URL/location") {
            parameter("page", page)
        }.body()

    /** Fetches the full detail of a single location by its [id]. */
    suspend fun fetchLocation(id: Int): LocationDto =
        client.get("$BASE_URL/location/$id").body()

    /**
     * Fetches characters by a list of [ids].
     *
     * The Rick and Morty API returns a single JSON object when one ID is requested
     * and a JSON array when several are requested. Both cases are handled explicitly
     * to keep deserialization unambiguous.
     */
    suspend fun fetchCharacters(ids: List<Int>): List<CharacterDto> {
        if (ids.isEmpty()) return emptyList()
        val idsParam = ids.joinToString(",")
        return if (ids.size == 1) {
            listOf(client.get("$BASE_URL/character/$idsParam").body())
        } else {
            client.get("$BASE_URL/character/$idsParam").body()
        }
    }
}
