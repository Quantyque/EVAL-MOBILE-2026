package com.example.eval_mobile_2026.data.remote

import com.example.eval_mobile_2026.data.remote.dto.CharacterDto
import com.example.eval_mobile_2026.data.remote.dto.LocationDto
import com.example.eval_mobile_2026.data.remote.dto.LocationPageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://rickandmortyapi.com/api"

class LocationService(private val client: HttpClient) {

    suspend fun fetchLocations(page: Int): LocationPageDto =
        client.get("$BASE_URL/location") {
            parameter("page", page)
        }.body()

    suspend fun fetchLocation(id: Int): LocationDto =
        client.get("$BASE_URL/location/$id").body()

    /**
     * Fetches character names for a list of IDs.
     *
     * The API returns a single object for one ID and an array for several,
     * so both cases are handled explicitly to keep deserialization sound.
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
