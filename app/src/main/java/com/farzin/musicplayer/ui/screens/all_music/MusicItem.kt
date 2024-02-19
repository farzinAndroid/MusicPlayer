package com.farzin.musicplayer.ui.screens.all_music

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.screens.components.MySpacerHeight
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun MusicItem(music: Music, onMusicClicked: () -> Unit) {

    val image = if (music.thumbnail.isEmpty()) painterResource(R.drawable.music_logo)
    else rememberAsyncImagePainter(music.thumbnail)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onMusicClicked() }
    ) {

        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .weight(0.2f)
        )

        Row(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
                .padding(start = 4.dp)
        ) {
            Column(modifier = Modifier.weight(0.9f)) {

                Text(
                    text = music.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.darkText,
                )

                MySpacerHeight(height = 4.dp)

                Text(
                    text = music.albumName,
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