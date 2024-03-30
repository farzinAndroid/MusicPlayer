package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.nav_graph.Screens
import com.farzin.musicplayer.viewmodels.AllSongsViewModel

@Composable
fun AllAlbums(
    navController: NavController,
    paddingValues: PaddingValues,
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
) {

    val albums by allSongsViewModel.allAlbums.collectAsState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        content = {
            items(albums){ album->
                AlbumCard(
                    album = album,
                    onClick = {
                        navController.navigate(Screens.Album.route + "?albumId=${album.albumId}")
                    }
                )
            }
        }
    )
}