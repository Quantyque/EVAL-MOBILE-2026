package com.example.eval_mobile_2026.data.repository

import com.example.eval_mobile_2026.data.local.LocationCache
import com.example.eval_mobile_2026.data.mapper.toDomain
import com.example.eval_mobile_2026.data.remote.LocationService
import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage
import com.example.eval_mobile_2026.domain.repository.LocationRepository

private const val MAX_RESIDENTS_DISPLAYED = 5

class LocationRepositoryImpl(
    private val service: LocationService,
    private val cache: LocationCache
) : LocationRepository {

    override suspend fun getLocations(page: Int): LocationPage {
        val cachedPage = cache.getPage(page)
        if (cachedPage != null) {
            // Return cached page; hasNextPage defaults to true for middle pages
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
