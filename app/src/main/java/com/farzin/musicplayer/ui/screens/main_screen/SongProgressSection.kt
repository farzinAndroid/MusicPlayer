package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun SongProgressSection(
    progress:Float,
    onProgressClicked:(Float)->Unit,
    durationString:String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        Text(
            text = durationString,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.darkText,
        )

        Slider(
            value =progress,
            onValueChange = { onProgressClicked(it) }
        )

    }
}