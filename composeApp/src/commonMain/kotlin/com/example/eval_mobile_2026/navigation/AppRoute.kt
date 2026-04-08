package com.example.eval_mobile_2026.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute {
    @Serializable
    data object LocationList : AppRoute()

    @Serializable
    data class LocationDetail(val locationId: Int) : AppRoute()
}
