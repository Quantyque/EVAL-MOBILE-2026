package com.example.eval_mobile_2026.data.repository

import com.example.eval_mobile_2026.data.local.LocationCache
import com.example.eval_mobile_2026.data.mapper.toDomain
import com.example.eval_mobile_2026.data.remote.LocationService
import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage
import com.example.eval_mobile_2026.domain.repository.LocationRepository

/** Maximum number of resident names fetched per location to avoid over-fetching. */
private const val MAX_RESIDENTS_DISPLAYED = 5

/**
 * Concrete implementation of [LocationRepository] that applies a cache-first, remote-fallback
 * fetch strategy:
 *
 * 1. **Read** from [LocationCache] (in-memory).
 * 2. On a cache miss, **fetch** from [LocationService] (remote API).
 * 3. **Write** the remote result back to the cache before returning.
 *
 * This ensures that data already loaded during list browsing is reused instantly
 * when the user navigates to the detail screen, with no duplicate network calls.
 */
class LocationRepositoryImpl(
    private val service: LocationService,
    private val cache: LocationCache
) : LocationRepository {

    override suspend fun getLocations(page: Int): LocationPage {
        val cachedPage = cache.getPage(page)
        if (cachedPage != null) {
            // Cache hit: pagination metadata is not stored locally, so hasNextPage defaults
            // to true for intermediate pages. The ViewModel preserves the totalCount from
            // the first successful remote call, so the UI remains consistent.
            return LocationPage(
                locations = cachedPage,
                hasNextPage = true,
                nextPageNumber = page + 1
            )
        }
        val result = service.fetchLocations(page).toDomain()
        cache.putPage(page, result.locations)
        return result
    }

    override suspend fun getLocation(id: Int): Location =
        cache.getById(id) ?: service.fetchLocation(id).toDomain().also { cache.put(it) }

    override suspend fun getResidentNames(ids: List<Int>): List<String> =
        service.fetchCharacters(ids.take(MAX_RESIDENTS_DISPLAYED)).map { it.name }
}
