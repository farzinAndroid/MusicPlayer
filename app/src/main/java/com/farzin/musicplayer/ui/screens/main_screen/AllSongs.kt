package com.farzin.musicplayer.ui.screens.main_screen

import android.util.Log
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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var sort by remember { mutableIntStateOf(0) }
    LaunchedEffect(dataStoreViewModel.getSort()){
        sort = dataStoreViewModel.getSort()
        allSongsViewModel.applySort(sort)
        /*when(sort){
            1->allSongsViewModel.setMediaItems(musicListDateAddedDesc)
            2->allSongsViewModel.setMediaItems(musicListDateAddedAsc)
            3->allSongsViewModel.setMediaItems(musicListNameDesc)
            4->allSongsViewModel.setMediaItems(musicListNameAsc)
        }*/
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
                                allSongsViewModel.setMediaItems(musicListDateAddedDesc)
                                allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }

                            }
                        }
                    )
                }
                2->{
                    InitLazyColumn(
                        songList = musicListDateAddedAsc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            scope.launch {
                                allSongsViewModel.setMediaItems(musicListDateAddedAsc)
                                allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }

                            }
                        }
                    )
                }
                3->{
                    InitLazyColumn(
                        songList = musicListNameDesc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            scope.launch {
                                allSongsViewModel.setMediaItems(musicListNameDesc)
                                allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }

                            }
                        }
                    )
                }
                4->{
                    InitLazyColumn(
                        songList = musicListNameAsc,
                        paddingValues = paddingValues,
                        onClick = {index->
                            allSongsViewModel.viewModelScope.launch {
                                allSongsViewModel.setMediaItems(musicListNameAsc)
                                allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                allSongsViewModel.isSongPlayingFromAlbum.emit(false)
                                if (!allSongsViewModel.isServiceRunning) {
                                    allSongsViewModel.startService(context)
                                    allSongsViewModel.isServiceRunning = true
                                }
                            }
                            Log.e("TAG","is album song = ${allSongsViewModel.isSongPlayingFromAlbum.value}")
                        }
                    )
                }
            }

        }
    }


}