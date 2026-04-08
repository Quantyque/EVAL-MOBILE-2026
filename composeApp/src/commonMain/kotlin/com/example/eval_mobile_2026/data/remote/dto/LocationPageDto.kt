package com.example.eval_mobile_2026.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationPageDto(
    val info: PageInfoDto,
    val results: List<LocationDto>
)

@Serializable
data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
