package com.example.eval_mobile_2026.data.mapper

import com.example.eval_mobile_2026.data.remote.dto.LocationDto
import com.example.eval_mobile_2026.data.remote.dto.LocationPageDto
import com.example.eval_mobile_2026.domain.model.Location
import com.example.eval_mobile_2026.domain.model.LocationPage

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentCount = residents.size,
    url = url,
    created = created
)

fun LocationPageDto.toDomain(): LocationPage = LocationPage(
    locations = results.map { it.toDomain() },
    hasNextPage = info.next != null,
    nextPageNumber = if (info.next != null) {
        // Extract the page number from the next URL query parameter
        info.next.substringAfterLast("page=").toIntOrNull()
    } else null
)
