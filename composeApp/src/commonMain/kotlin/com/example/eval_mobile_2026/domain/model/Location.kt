package com.example.eval_mobile_2026.domain.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    /** IDs of the characters present at this location, extracted from the resident URLs. */
    val residents: List<Int>,
    val url: String,
    val created: String
)
