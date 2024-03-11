package com.farzin.musicplayer.ui.screens.main_screen

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.nav_graph.Screens
import com.farzin.musicplayer.viewmodels.MainScreenViewModel
import com.farzin.musicplayer.viewmodels.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
) {


    var isPlaying by remember { mutableStateOf(false) }
    var currentSelectedSong by remember { mutableStateOf<Music>(Music()) }
    LaunchedEffect(true) {
        mainScreenViewModel.isPlaying.collectLatest {
            isPlaying = it
        }

    }
    LaunchedEffect(true) {
        mainScreenViewModel.currentSelectedSong.collectLatest {
            currentSelectedSong = it ?: Music(
                "", "No song", 0L, "", "", 0, "No song"
            )
        }
    }


    val context = LocalContext.current

    val songProgress by mainScreenViewModel.songProgress.collectAsState()
    val sliderProgress by mainScreenViewModel.sliderProgress.collectAsState()

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
        model = currentSelectedSong.albumArt,
        error = if (isSystemInDarkTheme()) painterResource(R.drawable.music_logo_light) else
            painterResource(R.drawable.music_logo_dark),
    )

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
                            mainScreenViewModel.onUIEvent(UIEvents.SeekToNext)
                            mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        onPreviousClicked = {
                            mainScreenViewModel.onUIEvent(UIEvents.SeekToPrevious)
                            mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        onPauseClicked = {
                            mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                        },
                        currentSelectedSong = currentSelectedSong,
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
                    mainScreenViewModel.onUIEvent(UIEvents.SeekTo(it))
                },
                onNextClicked = {
                    mainScreenViewModel.onUIEvent(UIEvents.SeekToNext)
                    mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                },
                onPreviousClicked = {
                    mainScreenViewModel.onUIEvent(UIEvents.SeekToPrevious)
                    mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                },
                onPauseClicked = {
                    mainScreenViewModel.onUIEvent(UIEvents.PlayPause)
                },
                currentSelectedSong = currentSelectedSong,
                isPlaying = isPlaying,
                onRepeatClicked = { /*TODO*/ },
                imagePainter = imagePainter,
                onCloseClicked = {
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )


        },
        scaffoldState = scaffoldState,
        topBar = {
            TopSearchSection(
                onMenuClicked = { /*TODO*/ },
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
            )
        },
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        containerColor = Color.Transparent,
        sheetDragHandle = null,
        sheetShape = bottomSheetShape,
    )

}

