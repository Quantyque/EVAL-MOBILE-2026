package com.example.eval_mobile_2026.audio

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine
import kotlin.math.PI
import kotlin.math.sin

actual class SoundManager actual constructor() {

    actual fun playDetailOpenSound() {
        Thread {
            try {
                val sampleRate = 44100
                val durationMs = 250
                val frequency = 880.0
                val numSamples = sampleRate * durationMs / 1000
                val buffer = ByteArray(numSamples * 2)

                for (i in 0 until numSamples) {
                    val sample = (sin(2.0 * PI * i * frequency / sampleRate) * Short.MAX_VALUE).toInt()
                    buffer[i * 2] = (sample and 0x00FF).toByte()
                    buffer[i * 2 + 1] = ((sample shr 8) and 0x00FF).toByte()
                }

                val format = AudioFormat(sampleRate.toFloat(), 16, 1, true, false)
                val info = DataLine.Info(SourceDataLine::class.java, format)
                val line = AudioSystem.getLine(info) as SourceDataLine
                line.open(format)
                line.start()
                line.write(buffer, 0, buffer.size)
                line.drain()
                line.close()
            } catch (_: Exception) {
                // Ignore if audio hardware is unavailable
            }
        }.also { it.isDaemon = true }.start()
    }

    actual fun release() = Unit
}
