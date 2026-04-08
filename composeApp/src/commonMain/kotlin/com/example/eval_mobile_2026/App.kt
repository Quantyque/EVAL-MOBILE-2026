package com.example.eval_mobile_2026

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.eval_mobile_2026.navigation.MobileNavGraph
import com.example.eval_mobile_2026.presentation.desktop.DesktopScreen

/**
 * Root composable and single entry point shared by Android and Desktop.
 *
 * Selects the appropriate layout at runtime based on [Platform.isDesktop]:
 * - Desktop → [DesktopScreen] (master-detail, no navigation stack)
 * - Mobile  → [MobileNavGraph] (stack navigation with back support)
 */
@Composable
fun App() {
    MaterialTheme {
        if (getPlatform().isDesktop) {
            DesktopScreen()
        } else {
            MobileNavGraph()
        }
    }
}
