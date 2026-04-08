package com.example.eval_mobile_2026.data.local

import com.example.eval_mobile_2026.domain.model.Location

/**
 * In-memory cache for [Location] objects, indexed in two ways:
 * - by location ID, for O(1) single-item lookup ([getById] / [put])
 * - by page number, for full-page retrieval ([getPage] / [putPage])
 *
 * Writing a page via [putPage] also populates the ID index, so a location
 * loaded as part of a list is immediately available for detail lookup without
 * a second network call.
 */
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
