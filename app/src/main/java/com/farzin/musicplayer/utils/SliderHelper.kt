package com.farzin.musicplayer.utils

object SliderHelper {

    fun convertForAudioWave(value: Float): Float {
        // Check for edge cases (0 and 1) to avoid division by zero
        if (value == 0.0f) {
            return 0.0f
        } else if (value == 100.0f) {
            return 1.0f
        }

        // Linear conversion with error handling
        val normalizedValue = value  / 100.0f
        if (normalizedValue < 0.0f || normalizedValue > 1.0f) {
            // Handle potential errors (e.g., values outside the range)
            println("Warning: Normalized value outside range: $normalizedValue")
        }
        return normalizedValue
    }

    fun convertBackToRange(normalizedValue: Float): Float {
        // Check for edge cases (0 and 1)
        if (normalizedValue == 0.0f) {
            return 0.0f
        } else if (normalizedValue == 1.0f) {
            return 100.0f
        }

        // Linear conversion
        val originalValue = normalizedValue * 100.0f   // Scale by the range
        return originalValue
    }

}