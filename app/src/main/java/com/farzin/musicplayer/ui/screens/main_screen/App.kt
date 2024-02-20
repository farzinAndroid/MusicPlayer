package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.nav_graph.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var music by remember { mutableStateOf(Music()) }

    val bottomSheetShape = if (scaffoldState.bottomSheetState.hasExpandedState) {
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

    BottomSheetScaffold(
        modifier = Modifier,
        sheetContent = {
            Card(
                modifier = Modifier
                    .height(60.dp),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    if (music.name.isEmpty()) {
                        Text(text = "no music playing")
                    } else {
                        Text(text = music.name)
                    }

                }

            }

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
                onSongSelected = {
                    music = it
                }
            )
        },
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        containerColor = Color.Transparent,
        sheetDragHandle = null,
        sheetShape =bottomSheetShape,
    )

}