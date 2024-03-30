package com.farzin.musicplayer.ui.screens.main_screen

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.nav_graph.Screens
import com.farzin.musicplayer.utils.SliderHelper.convertBackToRange
import com.farzin.musicplayer.viewmodels.AllSongsViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    allSongsViewModel: AllSongsViewModel = hiltViewModel(),
) {

    val activity = LocalContext.current as Activity

    var showSortDialogue by remember { mutableStateOf(false) }

    val isPlaying by allSongsViewModel.isPlaying.collectAsState()
    val currentSelectedSong by allSongsViewModel.currentSelectedSong.collectAsState()
    val amplitudes by allSongsViewModel.amplitudes.collectAsState()
    val songProgress by allSongsViewModel.songProgress.collectAsState()
    val sliderProgress by allSongsViewModel.sliderProgress.collectAsState()


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var isExpanded = when (scaffoldState.bottomSheetState.targetValue) {
        SheetValue.Hidden -> {
            false
        }

        SheetValue.Expanded -> {
            true
        }

        SheetValue.PartiallyExpanded -> {
            false
        }
    }


    val bottomSheetShape = if (!isExpanded) {
        RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp,
            bottomEnd = 28.dp,
            bottomStart = 28.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    }


    val imagePainter = rememberAsyncImagePainter(
        model = currentSelectedSong?.albumArt,
        error = if (isSystemInDarkTheme()) painterResource(R.drawable.music_logo_light) else
            painterResource(R.drawable.music_logo_dark),
    )


    if (showSortDialogue){
        Dialog(onDismissRequest = { showSortDialogue = false }) {
            SortDialogContent()
        }
    }
    BottomSheetScaffold(
        modifier = Modifier,
        sheetContent = {

            AnimatedVisibility(
                visible = !isExpanded,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SongController(
                        onNextClicked = {
                            allSongsViewModel.onUIEvent(UIEvents.SeekToNext)
                            allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        onPreviousClicked = {
                            allSongsViewModel.onUIEvent(UIEvents.SeekToPrevious)
                            allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        onPauseClicked = {
                            allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        currentSelectedSong = currentSelectedSong ?: emptyMusic(),
                        isPlaying = isPlaying,
                        imagePainter = imagePainter
                    )

                }
            }


            // if not expanded (on full screen)
            // show this
            SongFullDetail(
                songProgress = songProgress,
                sliderProgress = sliderProgress,
                onProgressBarClicked = {
                    allSongsViewModel.onUIEvent(UIEvents.SeekTo(convertBackToRange(it)))
                },
                onNextClicked = {
                    allSongsViewModel.onUIEvent(UIEvents.SeekToNext)
                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                },
                onPreviousClicked = {
                    allSongsViewModel.onUIEvent(UIEvents.SeekToPrevious)
                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                },
                onPauseClicked = {
                    allSongsViewModel.onUIEvent(UIEvents.PlayPause)
                },
                currentSelectedSong = currentSelectedSong ?: emptyMusic(),
                isPlaying = isPlaying,
                onRepeatClicked = { /*TODO*/ },
                imagePainter = imagePainter,
                onCloseClicked = {
                    if (isExpanded){
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    } else {
                        activity.moveTaskToBack(true)
                    }

                },
                amplitudes = amplitudes
            )


        },
        scaffoldState = scaffoldState,
        topBar = {
            TopSearchSection(
                onFilterClicked = { showSortDialogue = true },
                onCardClicked = {
                    navController.navigate(Screens.Search.route)
                }
            )
        },
        sheetPeekHeight = 60.dp,
        content = { paddingValues ->
            MainScreenTabLayout(
                navController = navController,
                paddingValues = paddingValues,
                onMusicClicked = {}
            )
        },
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        containerColor = Color.Transparent,
        sheetDragHandle = null,
        sheetShape = bottomSheetShape,
    )

}

private fun emptyMusic():Music = Music(
    "", "No song", 0L, "", "", 0, "No song"
)

