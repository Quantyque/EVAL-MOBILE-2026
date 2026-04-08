package com.example.eval_mobile_2026.audio

/**
 * Cross-platform audio feedback for UI interactions.
 *
 * Declared as an `expect` class so each platform provides its own implementation:
 * - **Android** (`SoundManager.android.kt`): [android.media.RingtoneManager] — plays the
 *   user's configured system notification sound. Requires an Android [android.content.Context],
 *   which is injected by the platform Koin module via [androidContext()][org.koin.android.ext.koin.androidContext].
 * - **Desktop/JVM** (`SoundManager.jvm.kt`): [java.awt.Toolkit.beep] — OS system alert sound.
 *
 * No constructor is declared here so each platform can define its own parameters without
 * leaking platform types (e.g. Android Context) into the common source set. Registration in
 * the DI graph is handled by each platform's [com.example.eval_mobile_2026.di.platformModule].
 */
expect class SoundManager {
    fun playDetailOpenSound()
    fun release()
}
