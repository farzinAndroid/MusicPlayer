package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.lightGray
import com.farzin.musicplayer.utils.SliderHelper.convertForAudioWave
import com.farzin.musicplayer.utils.TimeHelper.formatSongProgress
import com.farzin.musicplayer.utils.TimeHelper.stampTimeToDuration
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType


@Composable
fun SongProgressSection(
    songProgress: Long,
    sliderProgress: Float,
    onProgressBarClicked: (Float) -> Unit,
    duration: Long,
    amplitudes: List<Int>,
    progressColor:Color
) {

    var waveSliderProgress by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(sliderProgress) {
        waveSliderProgress = convertForAudioWave(sliderProgress)
    }


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
                .weight(0.2f),
            textAlign = TextAlign.Center
        )

        AudioWaveform(
            progress = waveSliderProgress,
            amplitudes = amplitudes,
            onProgressChange = { onProgressBarClicked(it) },
            modifier = Modifier
                .weight(0.6f)
                .padding(vertical = 10.dp),
            amplitudeType = AmplitudeType.Min,
            spikeWidth = 3.dp,
            progressBrush = SolidColor(progressColor),
            waveformBrush = SolidColor(MaterialTheme.colorScheme.lightGray),
        )

        Text(
            text = stampTimeToDuration(duration),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.darkText,
            modifier = Modifier
                .weight(0.2f),
            textAlign = TextAlign.Center
        )

    }
}
