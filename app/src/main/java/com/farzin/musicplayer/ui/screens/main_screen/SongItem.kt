package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.components.LottieLoading
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.theme.albumPlayColor
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.lightGray

@Composable
fun SongItem(
    music: Music,
    onMusicClicked: (Music) -> Unit,
    currentSelectedSong: Music,
    isPlaying: Boolean = false,
) {


    val isCurrentSong = currentSelectedSong == music

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
            .background(
                if (isCurrentSong)
                    MaterialTheme.colorScheme.albumPlayColor.copy(alpha = 0.4f)
                else
                    Color.Transparent
            )
            .clickable {
                onMusicClicked(music)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .weight(0.15f),
        ) {


            Image(
                painter = imagePainter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            if (isCurrentSong){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.lightGray.copy(0.5f)))
            }



            if (isCurrentSong) {
                LottieLoading(
                    isPlaying = isPlaying,
                )
            }
        }


        Row(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
                .padding(start = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.95f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

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