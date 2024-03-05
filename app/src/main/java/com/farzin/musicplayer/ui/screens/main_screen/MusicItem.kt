package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun MusicItem(music: Music, onMusicClicked: () -> Unit) {




    val imagePainter = rememberAsyncImagePainter(
        model = music.albumArt,
        error = if (isSystemInDarkTheme()) painterResource(R.drawable.music_logo_light) else
            painterResource(R.drawable.music_logo_dark),
    )


    MySpacerHeight(height = 8.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onMusicClicked()
            }
    ) {

        Image(
            painter = imagePainter,
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .weight(0.15f),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
                .padding(start = 4.dp)
        ) {
            Column(modifier = Modifier.weight(0.95f)) {

                Text(
                    text = music.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.darkText,
                )

                MySpacerHeight(height = 4.dp)

                Text(
                    text = music.artist,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                )

            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
            }

        }


    }

}