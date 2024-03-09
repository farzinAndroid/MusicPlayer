package com.farzin.musicplayer.ui.screens.main_screen

import android.graphics.Bitmap
import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.utils.PaletteGenerator

@Composable
fun SongFullDetail(
    progress: Long,
    onProgressBarClicked: (Long) -> Unit,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    currentSelectedSong: Music,
    isPlaying: Boolean,
    onRepeatClicked: () -> Unit,
    imagePainter: Painter,
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


    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (isBitmapNull) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(parseColor(muted)))
            )
        }

    }

}