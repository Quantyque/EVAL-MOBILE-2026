package com.example.eval_mobile_2026.data.remote.dto

import kotlinx.serialization.Serializable

/** Paginated response envelope for a list of locations. */
@Serializable
data class LocationPageDto(
    val info: PageInfoDto,
    val results: List<LocationDto>
)

/** Pagination metadata included in every list response from the API. */
@Serializable
data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
