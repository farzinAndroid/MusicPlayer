package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.viewModelScope
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.ui.screens.main_screen.InitLazyColumn
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AlbumDialog(
    albumId: Long,
    allSongsViewModel: AllSongsViewModel,
    onDismissRequest: () -> Unit
) {

    var albumMusicList by remember { mutableStateOf<List<Music>>(emptyList()) }
    val context = LocalContext.current
    val currentSelectedSong by allSongsViewModel.currentSelectedSong.collectAsState()

    Dialog(
        onDismissRequest = {
            albumMusicList = emptyList()
            onDismissRequest()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(1f)
                .background(Color.Black)
        ) {


            LaunchedEffect(albumId) {
                allSongsViewModel.getSongsBasedOnAlbum(albumId)
                allSongsViewModel.allSongsBasedOnAlbum.collectLatest {
                    albumMusicList = it
                }
            }

            InitLazyColumn(
                songList = albumMusicList,
                paddingValues = PaddingValues(),
                onClick = {index->
                    allSongsViewModel.viewModelScope.launch {
                        allSongsViewModel.isSongPlayingFromAlbum(true)
                        if (currentSelectedSong == albumMusicList[index]){
                            allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                        }else{
                            allSongsViewModel.setMediaItems(albumMusicList)
                            allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                        }

                        if (!allSongsViewModel.isServiceRunning) {
                            allSongsViewModel.startService(context)
                            allSongsViewModel.isServiceRunning = true
                        }
                    }
                }
            )
        }
    }


}