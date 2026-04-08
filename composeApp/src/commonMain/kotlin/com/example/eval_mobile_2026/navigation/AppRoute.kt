package com.example.eval_mobile_2026.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations for the mobile nav graph.
 *
 * Each subclass is annotated with [@Serializable][kotlinx.serialization.Serializable] so that
 * Jetpack Navigation can encode/decode route arguments automatically without string templates.
 * [LocationDetail.locationId] is serialized as a path parameter when navigating.
 */
@Serializable
sealed class AppRoute {
    @Serializable
    data object LocationList : AppRoute()

    @Serializable
    data class LocationDetail(val locationId: Int) : AppRoute()
}
