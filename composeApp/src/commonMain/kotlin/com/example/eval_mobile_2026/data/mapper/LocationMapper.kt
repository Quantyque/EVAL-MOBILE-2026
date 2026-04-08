package com.example.eval_mobile_2026.data.mapper

import com.example.eval_mobile_2026.data.remote.dto.LocationDto
import com.example.eval_mobile_2026.data.remote.dto.LocationPageDto
import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage

/**
 * Maps a [LocationDto] to its business model [Location].
 *
 * The [LocationDto.residents] field contains full API URLs (e.g. ".../character/42").
 * Only the trailing numeric ID is extracted; the rest of the URL is discarded so the
 * domain model stays free of any API-specific format.
 */
fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.mapNotNull { url -> url.substringAfterLast("/").toIntOrNull() },
    url = url,
    created = created
)

/** Maps a paginated API response to its business model [LocationPage]. */
fun LocationPageDto.toDomain(): LocationPage = LocationPage(
    locations = results.map { it.toDomain() },
    hasNextPage = info.next != null,
    nextPageNumber = if (info.next != null) {
        info.next.substringAfterLast("page=").toIntOrNull()
    } else null,
    totalCount = info.count
)
