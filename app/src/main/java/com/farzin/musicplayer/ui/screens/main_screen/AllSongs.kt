package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.viewmodels.AllMusicViewModel


@Composable
fun AllSongs(
    allMusicViewModel: AllMusicViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues,
    onSongSelected:(Music)->Unit
) {


    val allMusic by allMusicViewModel.musicList.collectAsState(emptyList())
    val context = LocalContext.current
    LaunchedEffect(true){
        allMusicViewModel.getAllMusic(context)
    }
//    val refreshState = rememberPullToRefreshState()
//
//
//    LaunchedEffect(refreshState.isRefreshing) {
//        allMusicViewModel.getAllMusic(context)
//
//        if (refreshState.isRefreshing) {
//            refreshState.startRefresh()
//            allMusicViewModel.getAllMusic(context)
//            delay(1000)
//            refreshState.endRefresh()
//        }
//    }


    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        items(allMusic) {
            MusicItem(music = it, onMusicClicked = {
                onSongSelected(it)
            })
        }

    }

}