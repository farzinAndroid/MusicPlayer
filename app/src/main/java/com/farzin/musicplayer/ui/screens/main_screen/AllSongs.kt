package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.viewmodels.MainScreenViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import com.farzin.musicplayer.viewmodels.UIState
import kotlinx.coroutines.launch


@Composable
fun AllSongs(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues,
    onSongSelected:(Music)->Unit
) {

    val musicList by mainScreenViewModel.musicList.collectAsState()
    val currentSelectedSong by mainScreenViewModel.currentSelectedSong.collectAsState(initial = Music())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
//    val refreshState = rememberPullToRefreshState()
//
//
//    LaunchedEffect(refreshState.isRefreshing) {
//        mainScreenViewModel.getAllMusic(context)
//
//        if (refreshState.isRefreshing) {
//            refreshState.startRefresh()
//            mainScreenViewModel.getAllMusic(context)
//            delay(1000)
//            refreshState.endRefresh()
//        }
//    }

    val uiState by mainScreenViewModel.uiState.collectAsState()
    when(uiState){
        UIState.Initial -> {
            Text(text = "Loading")
        }
        UIState.Ready -> {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                itemsIndexed(musicList) {index,music->
                    MusicItem(music = music, onMusicClicked = {
                        onSongSelected(music)
                        scope.launch {
                            mainScreenViewModel.onUIEvent(UIEvents.SelectedSongChange(index))
                            mainScreenViewModel.startService(context)
                        }
                    })
                }

            }
        }
    }



}