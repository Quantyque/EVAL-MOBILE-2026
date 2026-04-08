package com.example.eval_mobile_2026.audio

import android.media.AudioManager
import android.media.ToneGenerator

actual class SoundManager actual constructor() {

    private var toneGenerator: ToneGenerator? = null

    actual fun playDetailOpenSound() {
        try {
            toneGenerator?.release()
            toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
        } catch (_: Exception) {
            // Ignore if audio hardware is unavailable
        }
    }

    actual fun release() {
        toneGenerator?.release()
        toneGenerator = null
    }
}
