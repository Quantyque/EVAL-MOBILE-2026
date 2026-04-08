package com.example.eval_mobile_2026.audio

import android.content.Context
import android.media.RingtoneManager

/**
 * Android implementation of [SoundManager].
 *
 * Plays the user's configured system notification sound via [RingtoneManager], which uses
 * whatever notification tone the user has set in their Android system settings. This is
 * intentionally familiar and non-intrusive compared to a programmatically generated tone.
 *
 * A [Context] is required to resolve the notification URI and is provided by Koin via
 * `androidContext()` in the Android platform module — it never leaks into the common source set.
 */
actual class SoundManager(private val context: Context) {

    actual fun playDetailOpenSound() {
        try {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            RingtoneManager.getRingtone(context, uri)?.play()
        } catch (_: Exception) {
            // Ignore if audio is unavailable or URI cannot be resolved
        }
    }

    actual fun release() = Unit
}
