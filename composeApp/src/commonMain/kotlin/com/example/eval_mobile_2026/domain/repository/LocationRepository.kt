package com.example.eval_mobile_2026.domain.repository

import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage

interface LocationRepository {
    suspend fun getLocations(page: Int): LocationPage
    suspend fun getLocation(id: Int): Location
    /** Returns the names of the characters identified by [ids], limited to avoid over-fetching. */
    suspend fun getResidentNames(ids: List<Int>): List<String>
}
