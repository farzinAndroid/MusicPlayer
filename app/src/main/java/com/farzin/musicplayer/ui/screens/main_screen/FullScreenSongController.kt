package com.farzin.musicplayer.ui.screens.main_screen

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.RepeatOne
import androidx.compose.material.icons.rounded.RepeatOneOn
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.ShuffleOn
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.DataStoreViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FullScreenSongController(
    isPlaying: Boolean,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()

    var repeatMode by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        repeatMode = dataStoreViewModel.getRepeatMode() == 1
        allSongsViewModel.onUIEvent(UIEvents.SetRepeatMode(dataStoreViewModel.getRepeatMode()))
    }

    var shuffleMode by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        shuffleMode = dataStoreViewModel.getShuffleMode() == 1
        allSongsViewModel.onUIEvent(UIEvents.SetShuffleMode(dataStoreViewModel.getShuffleMode()))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {

        IconButton(onClick = {
            repeatMode = !repeatMode
            if (repeatMode) {
                dataStoreViewModel.saveRepeatMode(1)
            } else {
                dataStoreViewModel.saveRepeatMode(0)
            }

            scope.launch(Dispatchers.IO) {
                delay(200)
                allSongsViewModel.onUIEvent(UIEvents.SetRepeatMode(dataStoreViewModel.getRepeatMode()))
            }

        }
        ) {
            Icon(
                imageVector = if (!repeatMode) Icons.Rounded.RepeatOne else Icons.Rounded.RepeatOneOn,
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
                imageVector = Icons.Rounded.SkipPrevious,
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
                    color = Color.Gray
                )
            })

            Icon(
                imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
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
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp),
                tint = MaterialTheme.colorScheme.darkText
            )
        }





        IconButton(onClick = {
            shuffleMode = !shuffleMode
            if (shuffleMode) {
                dataStoreViewModel.saveShuffleMode(1)
            } else {
                dataStoreViewModel.saveShuffleMode(0)
            }


            scope.launch(Dispatchers.IO) {
                delay(200)
                allSongsViewModel.onUIEvent(UIEvents.SetShuffleMode(dataStoreViewModel.getShuffleMode()))
            }

        }) {
            Icon(
                imageVector = if (shuffleMode) Icons.Rounded.ShuffleOn else Icons.Rounded.Shuffle,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.darkText
            )
        }

    }


}