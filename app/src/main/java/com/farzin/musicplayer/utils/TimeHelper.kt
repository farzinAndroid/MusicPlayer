package com.farzin.musicplayer.utils

import java.util.concurrent.TimeUnit
import kotlin.math.floor

object TimeHelper {

    fun formatSongProgress(progress: Long): String {
        val minute = TimeUnit.MILLISECONDS.toMinutes(progress)
        val remainingSeconds = progress - TimeUnit.MINUTES.toMillis(minute)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSeconds)
        val formattedRemainingSeconds =
            if (seconds < 10) "0$seconds" else seconds
        return "$minute:$formattedRemainingSeconds"
    }

    fun stampTimeToDuration(duration: Long): String {
        if (duration <= 0) {
            return "--:--"
        }
        val totalSecond = floor(duration / 1E3).toInt()
        val remainingSeconds = totalSecond % 60  // Get remaining seconds using modulo
        val formattedRemainingSeconds =
            if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds
        val minute = (totalSecond - remainingSeconds) / 60
        return "$minute:$formattedRemainingSeconds"
    }
}