package com.example.eval_mobile_2026.audio

import java.awt.Toolkit

/**
 * Desktop (JVM) implementation of [SoundManager].
 *
 * Delegates to [Toolkit.beep], which plays the OS-level system alert sound
 * (configurable by the user in their OS settings). This approach is platform-native,
 * thread-safe, and requires no audio file or manual signal generation.
 */
actual class SoundManager {

    actual fun playDetailOpenSound() {
        try {
            Toolkit.getDefaultToolkit().beep()
        } catch (_: Exception) {
            // Ignore if audio is unavailable in the current environment
        }
    }

    actual fun release() = Unit
}
