package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.likeColor

@Composable
fun FullScreenSongController(
    isPlaying: Boolean,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    onRepeatClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {

        IconButton(onClick = onRepeatClicked) {
            Icon(
                imageVector = Icons.Filled.Repeat,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.darkText
            )
        }

        IconButton(onClick = {
            onPreviousClicked()
        }) {
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp),
                tint = MaterialTheme.colorScheme.darkText
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onPauseClicked()
                }, onDraw = {
                drawCircle(
                    color = Color.LightGray
                )
            })

            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp),
                tint = MaterialTheme.colorScheme.darkText
            )

        }

        IconButton(onClick = {
            onNextClicked()
        }) {
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp),
                tint = MaterialTheme.colorScheme.darkText
            )
        }





        IconButton(onClick = onRepeatClicked) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.likeColor
            )
        }

    }


}