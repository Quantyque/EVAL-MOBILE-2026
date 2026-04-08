package com.example.eval_mobile_2026

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "EVALMOBILE2026",
    ) {
        App()
    }
}