package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.farzin.musicplayer.ui.components.Loading
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.DataStoreViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import com.farzin.musicplayer.viewmodels.UIState
import kotlinx.coroutines.launch


@Composable
fun AllSongs(
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues,
    onMusicClicked: () -> Unit,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
) {



    val musicListDateAddedDesc by allSongsViewModel.musicListDateAddedDesc.collectAsState()
    val musicListDateAddedAsc by allSongsViewModel.musicListDateAddedAsc.collectAsState()
    val musicListNameDesc by allSongsViewModel.musicListNameDesc.collectAsState()
    val musicListNameAsc by allSongsViewModel.musicListNameAsc.collectAsState()
    val currentSelectedSong by allSongsViewModel.currentSelectedSong.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var sort by remember { mutableIntStateOf(0) }
    LaunchedEffect(dataStoreViewModel.getSort()){
        sort = dataStoreViewModel.getSort()
        allSongsViewModel.applySort(sort)
    }


    val uiState by allSongsViewModel.uiState.collectAsState()
    when (uiState) {
        UIState.Initial -> {
            Loading()
        }

        UIState.Ready -> {
            when(sort){
                1->{
                    InitLazyColumn(
                        songList = musicListDateAddedDesc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            scope.launch {
                                allSongsViewModel.isSongPlayingFromAlbum(false)
                                if (currentSelectedSong == musicListDateAddedDesc[index]){
                                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                                }else{
                                    allSongsViewModel.setMediaItems(musicListDateAddedDesc)
                                    allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                }
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }
                            }
                        },
                        currentSelectedSong = currentSelectedSong ?: emptyMusic()
                    )
                }
                2->{
                    InitLazyColumn(
                        songList = musicListDateAddedAsc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            scope.launch {
                                allSongsViewModel.isSongPlayingFromAlbum(false)
                                if (currentSelectedSong == musicListDateAddedAsc[index]){
                                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                                }else{
                                    allSongsViewModel.setMediaItems(musicListDateAddedAsc)
                                    allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                }
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }
                            }
                        },
                        currentSelectedSong = currentSelectedSong ?: emptyMusic()
                    )
                }
                3->{
                    InitLazyColumn(
                        songList = musicListNameDesc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            scope.launch {
                                allSongsViewModel.isSongPlayingFromAlbum(false)
                                if (currentSelectedSong == musicListNameDesc[index]){
                                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                                }else{
                                    allSongsViewModel.setMediaItems(musicListNameDesc)
                                    allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                }
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }
                            }
                        },
                        currentSelectedSong = currentSelectedSong ?: emptyMusic()
                    )
                }
                4->{
                    InitLazyColumn(
                        songList = musicListNameAsc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            allSongsViewModel.viewModelScope.launch {
                                allSongsViewModel.isSongPlayingFromAlbum(false)
                                if (currentSelectedSong == musicListNameAsc[index]){
                                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                                }else{
                                    allSongsViewModel.setMediaItems(musicListNameAsc)
                                    allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                }
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }
                            }
                        },
                        currentSelectedSong = currentSelectedSong ?: emptyMusic()
                    )
                }
            }

        }
    }


}