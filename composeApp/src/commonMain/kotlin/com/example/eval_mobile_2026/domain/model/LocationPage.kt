package com.example.eval_mobile_2026.domain.model

/**
 * Wraps a single page of [Location] results with its pagination metadata.
 *
 * [totalCount] reflects the total number of locations reported by the remote API.
 * It defaults to 0 when the page is served from the local cache, since the cache
 * does not store pagination metadata independently.
 */
data class LocationPage(
    val locations: List<Location>,
    val hasNextPage: Boolean,
    val nextPageNumber: Int?,
    val totalCount: Int = 0
)
