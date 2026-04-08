package com.example.eval_mobile_2026

import android.content.Context
import com.example.eval_mobile_2026.audio.SoundManager

/**
 * Factory function for creating a [SoundManager] scoped to this [Context].
 * Useful when you need to create a SoundManager outside of the dependency injection graph.
 */
fun Context.createSoundManager(): SoundManager = SoundManager()
