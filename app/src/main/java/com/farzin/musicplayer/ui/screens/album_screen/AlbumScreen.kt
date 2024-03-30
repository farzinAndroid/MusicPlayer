package com.farzin.musicplayer.ui.screens.album_screen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.screens.main_screen.InitLazyColumn
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AlbumScreen(
    albumId: Long,
    navController: NavController,
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
) {

    var songsBasedOnAlbum by remember { mutableStateOf<List<Music>>(emptyList()) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(albumId){
        allSongsViewModel.getSongsBasedOnAlbum(albumId)
        allSongsViewModel.allSongsBasedOnAlbum.collectLatest {
            songsBasedOnAlbum = it
        }
    }


    InitLazyColumn(
        songList = songsBasedOnAlbum,
        paddingValues = PaddingValues(),
        onClick = {
            allSongsViewModel.viewModelScope.launch {
                allSongsViewModel.setMediaItems(songsBasedOnAlbum)
                allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(it))
                allSongsViewModel.isSongPlayingFromAlbum.emit(true)
                if (!allSongsViewModel.isServiceRunning) {
                    allSongsViewModel.startService(context)
                    allSongsViewModel.isServiceRunning = true
                }
                Log.e("TAG","is album song = ${allSongsViewModel.isSongPlayingFromAlbum.value}")
            }
        }
    )
    
}