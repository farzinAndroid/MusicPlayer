package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farzin.musicplayer.data.model.Music

@Composable
fun InitLazyColumn(
    songList: List<Music>,
    paddingValues: PaddingValues,
    onClick: (Int) -> Unit,
    currentSelectedSong: Music,
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        itemsIndexed(songList) { index, music ->
            SongItem(
                music = music,
                onMusicClicked = {
                    onClick(index)
                },
                currentSelectedSong
            )
        }

    }
}