package com.farzin.musicplayer.ui.screens.main_screen

import android.graphics.Bitmap
import android.graphics.Color.parseColor
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.theme.albumPlayColor
import com.farzin.musicplayer.ui.theme.mainBackground
import com.farzin.musicplayer.utils.PaletteGenerator

@Composable
fun SongFullDetail(
    songProgress: Long,
    sliderProgress: Float,
    onProgressBarClicked: (Float) -> Unit,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    currentSelectedSong: Music,
    isPlaying: Boolean,
    imagePainter: AsyncImagePainter,
    onCloseClicked: () -> Unit,
    amplitudes: List<Int>,
) {

    val context = LocalContext.current
    var isBitmapNull by remember { mutableStateOf(true) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var colors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    var vibrant by remember { mutableStateOf("") }
    var darkVibrant by remember { mutableStateOf("") }
    var lightVibrant by remember { mutableStateOf("") }
    var lightMuted by remember { mutableStateOf("") }
    var muted by remember { mutableStateOf("") }
    var darkMuted by remember { mutableStateOf("") }

    LaunchedEffect(currentSelectedSong) {
        // Reset colors to default black when a new song is selected
        vibrant = "#000000"
        darkVibrant = "#000000"
        lightVibrant = "#000000"
        lightMuted = "#000000"
        muted = "#000000"
        darkMuted = "#000000"

        try {
            bitmap = PaletteGenerator.convertImageUrlToBitmap(
                imageUrl = currentSelectedSong.albumArt,
                context = context
            )
            Log.e("TAG", bitmap.toString())
            if (bitmap != null) {
                isBitmapNull = false
                colors = PaletteGenerator.extractColorsFromBitmap(bitmap!!)
            } else {
                // If bitmap is null, keep the colors as default
                isBitmapNull = true
            }
        } catch (e: Exception) {
            Log.e("TAG", "no image")
            // If an exception occurs, ensure that colors are set to default
            isBitmapNull = true
        }

    }

    LaunchedEffect(bitmap) {
        if (!isBitmapNull) {
            vibrant = colors["vibrant"] ?: "#000000"
            darkVibrant = colors["darkVibrant"] ?: "#000000"
            lightVibrant = colors["lightVibrant"] ?: "#000000"
            lightMuted = colors["lightMuted"] ?: "#000000"
            muted = colors["muted"] ?: "#000000"
            darkMuted = colors["darkMuted"] ?: "#000000"
            Log.e(
                "TAG",
                "$vibrant - $darkVibrant - $lightVibrant - $lightMuted - $muted - $darkMuted"
            )
        }
    }

    val backGroundColor = if (isBitmapNull)
        MaterialTheme.colorScheme.mainBackground
    else
        Color(parseColor(muted))

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backGroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .align(Alignment.TopCenter)
                )
                {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { onCloseClicked() }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(26.dp)
                            )
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(26.dp)
                            )
                        }
                    }

                    Image(
                        painter = imagePainter,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.65f)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(38.dp))
                            .then(if (imagePainter.state is AsyncImagePainter.State.Success) {
                                Modifier.shadow(8.dp, shape = RoundedCornerShape(38.dp))
                            } else {
                                Modifier
                            }),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(
                topStart = 20.dp, topEnd = 20.dp
            )
        ) {
            SongTitleAndArtist(currentSelectedSong.title, currentSelectedSong.artist)

            MySpacerHeight(height = 16.dp)

            SongProgressSection(
                songProgress = songProgress,
                onProgressBarClicked = onProgressBarClicked,
                duration = currentSelectedSong.duration.toLong(),
                sliderProgress = sliderProgress,
                amplitudes = amplitudes,
                progressColor = MaterialTheme.colorScheme.albumPlayColor
            )

            MySpacerHeight(height = 20.dp)
            
            FullScreenSongController(
                isPlaying = isPlaying,
                onNextClicked = onNextClicked,
                onPreviousClicked = onPreviousClicked,
                onPauseClicked = onPauseClicked
            )
        }

    }


    BackHandler {
        onCloseClicked()
    }

}



