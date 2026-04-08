package com.example.eval_mobile_2026.di

import com.example.eval_mobile_2026.audio.SoundManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android-specific Koin module.
 *
 * Provides:
 * - [SoundManager] with the application [Context] so it can play system notification sounds
 *   via [android.media.RingtoneManager].
 * - [HttpClient] backed by the Ktor Android engine. Both are declared here rather than in
 *   [com.example.eval_mobile_2026.di.appModule] because they depend on Android-only types.
 */
val platformModule = module {
    single { SoundManager(androidContext()) }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
}
