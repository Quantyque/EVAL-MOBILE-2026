package com.example.eval_mobile_2026.domain.repository

import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage

/**
 * Contract for accessing location data.
 *
 * Implementations are responsible for deciding the fetch strategy (remote, local cache,
 * or a combination). Callers in the Presentation layer interact only with this interface
 * and remain unaware of any network or storage details.
 */
interface LocationRepository {

    /**
     * Returns a page of locations. The implementation may serve the result from a local
     * cache before falling back to the remote API.
     */
    suspend fun getLocations(page: Int): LocationPage

    /**
     * Returns the full detail of the location identified by [id].
     * A cached value is returned if available; otherwise the remote API is queried.
     */
    suspend fun getLocation(id: Int): Location

    /**
     * Returns the names of the characters identified by [ids].
     * The list is capped internally to avoid over-fetching on locations with many residents.
     */
    suspend fun getResidentNames(ids: List<Int>): List<String>
}
