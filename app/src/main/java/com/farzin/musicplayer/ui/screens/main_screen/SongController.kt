package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.components.MySpacerWidth
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.searchBarColor

@Composable
fun SongController(
    onNextClicked:()->Unit,
    onPreviousClicked:()->Unit,
    onPauseClicked:()->Unit,
    currentSelectedSong: Music,
    isPlaying:Boolean,
    imagePainter:Painter
) {



    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.searchBarColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )

            MySpacerWidth(width = 8.dp)

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text =currentSelectedSong.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.darkText,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                MySpacerHeight(height = 4.dp)

                Text(
                    text =currentSelectedSong.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.darkText,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {
                onPreviousClicked()
            }) {
                Icon(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "",
                    modifier = Modifier
                        .size(22.dp),
                    tint = MaterialTheme.colorScheme.darkText
                )
            }

            IconButton(onClick = {
                onPauseClicked()
            }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier
                        .size(22.dp),
                    tint = MaterialTheme.colorScheme.darkText
                )
            }


            IconButton(onClick = {
                onNextClicked()
            }) {
                Icon(
                    imageVector =Icons.Rounded.SkipNext,
                    contentDescription = "",
                    modifier = Modifier
                        .size(22.dp),
                    tint = MaterialTheme.colorScheme.darkText
                )
            }

        }

    }

}