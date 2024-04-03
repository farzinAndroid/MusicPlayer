package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.farzin.musicplayer.ui.components.MySpacerWidth
import com.farzin.musicplayer.ui.theme.albumPlayColor

@Composable
fun AlbumDialogPictureSection(
    modifier: Modifier = Modifier,
    onPlayClicked:()->Unit,
    imagePainter:AsyncImagePainter
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.40f)
            .padding(start = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "",
            modifier = Modifier
                .height(130.dp)
                .width(120.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            contentScale = ContentScale.Crop
        )

        MySpacerWidth(width = 30.dp)

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.albumPlayColor),
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = { onPlayClicked() }) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }

}