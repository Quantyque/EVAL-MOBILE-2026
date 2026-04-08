package com.example.eval_mobile_2026

/**
 * Runtime platform descriptor.
 *
 * [isDesktop] is the primary flag used by [App] to switch between the master-detail
 * Desktop layout and the stack-based Mobile navigation graph.
 */
interface Platform {
    val name: String
    val isDesktop: Boolean
}

expect fun getPlatform(): Platform