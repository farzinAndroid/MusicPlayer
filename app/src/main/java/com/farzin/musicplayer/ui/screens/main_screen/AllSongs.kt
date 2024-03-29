package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.ui.components.Loading
import com.farzin.musicplayer.viewmodels.DataStoreViewModel
import com.farzin.musicplayer.viewmodels.MainScreenViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import com.farzin.musicplayer.viewmodels.UIState
import kotlinx.coroutines.launch


@Composable
fun AllSongs(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues,
    onMusicClicked: () -> Unit,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
) {



    val musicListDateAddedDesc by mainScreenViewModel.musicListDateAddedDesc.collectAsState()
    val musicListDateAddedAsc by mainScreenViewModel.musicListDateAddedAsc.collectAsState()
    val musicListNameDesc by mainScreenViewModel.musicListNameDesc.collectAsState()
    val musicListNameAsc by mainScreenViewModel.musicListNameAsc.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isServiceRunning by remember { mutableStateOf(false) }

    var sort by remember { mutableIntStateOf(0) }
    LaunchedEffect(dataStoreViewModel.getSort()){
        sort = dataStoreViewModel.getSort()
        mainScreenViewModel.applySort(sort)
        when(sort){
            1->mainScreenViewModel.setMediaItems(musicListDateAddedDesc)
            2->mainScreenViewModel.setMediaItems(musicListDateAddedAsc)
            3->mainScreenViewModel.setMediaItems(musicListNameDesc)
            4->mainScreenViewModel.setMediaItems(musicListNameAsc)
        }
    }


    val uiState by mainScreenViewModel.uiState.collectAsState()
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
                                mainScreenViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!isServiceRunning) {
                                    mainScreenViewModel.startService(context)
                                    isServiceRunning = true
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
                                mainScreenViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!isServiceRunning) {
                                    mainScreenViewModel.startService(context)
                                    isServiceRunning = true
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
                                mainScreenViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!isServiceRunning) {
                                    mainScreenViewModel.startService(context)
                                    isServiceRunning = true
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
                            scope.launch {
                                mainScreenViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                                if (!isServiceRunning) {
                                    mainScreenViewModel.startService(context)
                                    isServiceRunning = true
                                }
                            }
                        }
                    )
                }
            }

        }
    }


}