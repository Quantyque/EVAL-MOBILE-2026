package com.example.eval_mobile_2026

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform