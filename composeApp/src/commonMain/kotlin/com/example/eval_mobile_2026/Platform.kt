package com.example.eval_mobile_2026

interface Platform {
    val name: String
    val isDesktop: Boolean
}

expect fun getPlatform(): Platform