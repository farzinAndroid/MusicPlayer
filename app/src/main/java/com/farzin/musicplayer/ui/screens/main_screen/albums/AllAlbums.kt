package com.farzin.musicplayer.ui.screens.main_screen.albums

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.musicplayer.viewmodels.AlbumsViewModel
import com.farzin.musicplayer.viewmodels.DataStoreViewModel

@Composable
fun AllAlbums(
    albumsViewModel: AlbumsViewModel = hiltViewModel(),
    navController : NavController,
    paddingValues: PaddingValues,
    onMusicClicked: () -> Unit,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
) {

    val albumsDateDesc by albumsViewModel.allAlbumsDateDesc.collectAsState()
    val songsBasedOnAlbum by albumsViewModel.allSongsBasedOnAlbum.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(albumsDateDesc){
            Text(text = it.albumName, modifier = Modifier
                .clickable {
                    albumsViewModel.getSongsBasedOnAlbum(it.albumId)
                    Log.e("TAG",songsBasedOnAlbum.toString())
                }
            )
        }
    }

}