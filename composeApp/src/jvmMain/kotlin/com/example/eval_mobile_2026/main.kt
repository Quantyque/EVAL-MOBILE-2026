package com.example.eval_mobile_2026

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.eval_mobile_2026.di.appModule
import com.example.eval_mobile_2026.di.platformModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule, platformModule)
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Rick & Morty — Locations",
        ) {
            App()
        }
    }
}
