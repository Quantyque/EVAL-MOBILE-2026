package com.example.eval_mobile_2026

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.eval_mobile_2026.navigation.MobileNavGraph
import com.example.eval_mobile_2026.presentation.desktop.DesktopScreen

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
