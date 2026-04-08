package com.example.eval_mobile_2026.domain.repository

import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage

interface LocationRepository {
    suspend fun getLocations(page: Int): LocationPage
    suspend fun getLocation(id: Int): Location
}
