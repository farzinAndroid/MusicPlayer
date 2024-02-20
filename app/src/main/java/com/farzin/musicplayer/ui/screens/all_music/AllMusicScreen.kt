package com.farzin.musicplayer.ui.screens.all_music

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.viewmodels.AllMusicViewModel
import kotlinx.coroutines.delay


@Composable
fun AllMusicScreen(
    navController: NavController,
) {
    Home(navController = navController)

}

@Composable
fun Home(allMusicViewModel: AllMusicViewModel = hiltViewModel(), navController: NavController) {


    SwipeRefreshSection(allMusicViewModel = allMusicViewModel, navController = navController)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeRefreshSection(allMusicViewModel: AllMusicViewModel, navController: NavController) {

    val allMusic by allMusicViewModel.musicList.collectAsState(emptyList())
    val context = LocalContext.current
    val refreshState = rememberPullToRefreshState()


    LaunchedEffect(refreshState.isRefreshing) {
        allMusicViewModel.getAllMusic(context)

        if (refreshState.isRefreshing) {
            refreshState.startRefresh()
            allMusicViewModel.getAllMusic(context)
            delay(1000)
            refreshState.endRefresh()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(refreshState.nestedScrollConnection)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                TopSearchSection(
                    onMenuClicked = {},
                    onCardClicked = {}
                )
            }

            if (!refreshState.isRefreshing) {
                items(allMusic) {
                    MusicItem(music = it, onMusicClicked = {

                    })
                }
            }

        }


        PullToRefreshContainer(
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }


}