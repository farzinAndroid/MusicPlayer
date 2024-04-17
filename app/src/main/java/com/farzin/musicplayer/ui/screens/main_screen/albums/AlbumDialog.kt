package com.farzin.musicplayer.ui.screens.main_screen.albums

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.model.MusicAlbum
import com.farzin.musicplayer.ui.screens.main_screen.InitLazyColumn
import com.farzin.musicplayer.ui.screens.main_screen.emptyMusic
import com.farzin.musicplayer.ui.theme.mainBackground
import com.farzin.musicplayer.ui.theme.searchBarColor
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AlbumDialog(
    album: MusicAlbum,
    allSongsViewModel: AllSongsViewModel,
    onDismissRequest: () -> Unit,
) {

    var albumMusicList by remember { mutableStateOf<List<Music>>(emptyList()) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentSelectedSong by allSongsViewModel.currentSelectedSong.collectAsState()


    LaunchedEffect(album.albumId) {
        allSongsViewModel.getSongsBasedOnAlbum(album.albumId)
        allSongsViewModel.allSongsBasedOnAlbum.collectLatest {
            albumMusicList = it
        }
    }


    val imagePainter = rememberAsyncImagePainter(
        model = album.albumArt,
        error = if (isSystemInDarkTheme()) painterResource(R.drawable.music_logo_light) else
            painterResource(R.drawable.music_logo_dark),
    )

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
                .background(MaterialTheme.colorScheme.mainBackground),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.35f)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .clip(RoundedCornerShape(bottomEnd = 80.dp))
                        .background(MaterialTheme.colorScheme.searchBarColor),
                )


                AlbumDialogTitleSection(
                    onBackClicked = onDismissRequest,
                    albumTitle = album.albumName,
                    artist = album.artistName
                )
                AlbumDialogPictureSection(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    imagePainter = imagePainter,
                    onPlayClicked = {
                        allSongsViewModel.setMediaItems(albumMusicList)
                        allSongsViewModel.onUIEvent(UIEvents.SelectedSongChange(0))
                    }
                )


            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxHeight(0.40f)
            ) {
                InitLazyColumn(
                    songList = albumMusicList,
                    paddingValues = PaddingValues(),
                    onClick = { index ->
                        scope.launch {
                            allSongsViewModel.isSongPlayingFromAlbum(true)
                            if (currentSelectedSong == albumMusicList[index]) {
                                allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                            } else {
                                allSongsViewModel.setMediaItems(albumMusicList)
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