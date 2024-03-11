package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.utils.TimeHelper.formatSongProgress
import com.farzin.musicplayer.utils.TimeHelper.stampTimeToDuration
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongProgressSection(
    songProgress:Long,
    sliderProgress:Float,
    onProgressBarClicked:(Float)->Unit,
    duration:Long
) {

    var amplitudeType by remember { mutableStateOf(AmplitudeType.Avg) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        Text(
            text = formatSongProgress(songProgress),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.darkText,
            modifier = Modifier
                .weight(0.1f)
        )

        AudioWaveform(
            progress = sliderProgress,
            amplitudes = listOf(1,2,4,5),
            amplitudeType = amplitudeType,
            onProgressChange = { onProgressBarClicked(it) },
            modifier = Modifier
                .weight(0.8f)
        )
        /*Slider(
            value =sliderProgress,
            onValueChange = { onProgressBarClicked(it) },
            valueRange = 0f..100f,
            modifier = Modifier
                .weight(0.8f),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.darkText,
                activeTrackColor = MaterialTheme.colorScheme.darkText,
                inactiveTrackColor = Color.Gray,
            ),
            thumb = {
                Spacer(
                    Modifier
                        .size(10.dp)
                        .background(MaterialTheme.colorScheme.darkText, CircleShape)
                        .align(Alignment.CenterVertically)
                )
            }

        )*/

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
