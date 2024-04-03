package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.data.model.MusicAlbum
import com.farzin.musicplayer.viewmodels.AllSongsViewModel

@Composable
fun AllAlbums(
    navController: NavController,
    paddingValues: PaddingValues,
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
) {

    val albums by allSongsViewModel.allAlbums.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf(MusicAlbum()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (showDialog) {
            AlbumDialog(
               album = selectedAlbum,
                allSongsViewModel = allSongsViewModel,
                onDismissRequest = {
                    showDialog = false
                })
        }


        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            content = {
                items(albums) { album ->
                    AlbumCard(
                        album = album,
                        onClick = {
                            showDialog = true
                            selectedAlbum = it
                        }
                    )
                }
            }
        )
    }


}