package com.example.eval_mobile_2026.di

import com.example.eval_mobile_2026.audio.SoundManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Desktop (JVM) specific Koin module.
 *
 * Provides:
 * - [SoundManager] (no constructor arguments needed on JVM).
 * - [HttpClient] backed by the Ktor Java engine. Both are declared here rather than in
 *   [com.example.eval_mobile_2026.di.appModule] because the engine class ([Java]) is only
 *   available in the `jvmMain` source set.
 */
val platformModule = module {
    single { SoundManager() }
    single {
        HttpClient(Java) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
}
