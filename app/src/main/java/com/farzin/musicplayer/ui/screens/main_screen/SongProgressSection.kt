package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun SongProgressSection(
    progress:Float,
    progressString:String,
    onProgressBarClicked:(Float)->Unit,
    duration:Long
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        Text(
            text = formatDuration(progress,duration),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.darkText,
            modifier = Modifier
                .weight(0.1f)
        )

        Slider(
            value =progress,
            onValueChange = { onProgressBarClicked(it) },
            valueRange = 0f..100f,
            modifier = Modifier
                .weight(0.8f),
            colors = SliderDefaults.colors(
                activeTickColor = MaterialTheme.colorScheme.darkText
            )
        )

        Text(
            text = stampTimeToDuration(duration),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.darkText,
            modifier = Modifier
                .weight(0.1f)
        )

    }
}


private fun formatDuration(progress: Float,duration:Long): String {
    val totalDuration = duration
    val totalSeconds = totalDuration / 1000 // Convert total duration to seconds
    val currentSeconds = (progress * totalSeconds).toInt() // Calculate current seconds

    val hours = currentSeconds / 3600 // Get hours (integer division)
    val remainingSeconds = currentSeconds % 3600 // Get remaining seconds after hours

    val minutes = remainingSeconds / 60 % 60 // Get minutes within 0-59 range
    val seconds = remainingSeconds % 60

    // Format the time string with leading zeros for hours, minutes, and seconds
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
