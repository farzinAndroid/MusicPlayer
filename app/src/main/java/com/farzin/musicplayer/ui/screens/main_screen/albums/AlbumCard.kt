package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.MusicAlbum
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.searchBarColor

@Composable
fun AlbumCard(
    album: MusicAlbum,
    onClick: (Long) -> Unit,
) {


    val imagePainter = rememberAsyncImagePainter(
        model = album.albumArt,
        error = if (isSystemInDarkTheme()) painterResource(R.drawable.music_logo_light) else
            painterResource(R.drawable.music_logo_dark),
    )


    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(0.33f)
            .height(200.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.searchBarColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(album.albumId)
                }
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "",
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = album.albumName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.darkText,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                MySpacerHeight(height = 4.dp)

                Text(
                    text = album.artistName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

            }

        }

    }

}