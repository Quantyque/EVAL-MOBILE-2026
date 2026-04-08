package com.example.eval_mobile_2026.data.local

import com.example.eval_mobile_2026.domain.model.Location

// In-memory cache keyed by location ID and page number.
class LocationCache {
    private val locationsById = mutableMapOf<Int, Location>()
    private val pageIndex = mutableMapOf<Int, List<Location>>()

    fun getById(id: Int): Location? = locationsById[id]

    fun getPage(page: Int): List<Location>? = pageIndex[page]

    fun put(location: Location) {
        locationsById[location.id] = location
    }

    fun putPage(page: Int, locations: List<Location>) {
        pageIndex[page] = locations
        locations.forEach { locationsById[it.id] = it }
    }
}
