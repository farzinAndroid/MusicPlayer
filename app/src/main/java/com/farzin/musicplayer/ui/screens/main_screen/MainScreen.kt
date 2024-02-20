package com.farzin.musicplayer.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val isExpanded = when(scaffoldState.bottomSheetState.targetValue){
        SheetValue.Hidden -> {false}
        SheetValue.Expanded -> {true}
        SheetValue.PartiallyExpanded -> {false}
    }


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
            if (!isExpanded){
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .height(60.dp)
                            .clickable { scope.launch { scaffoldState.bottomSheetState.expand() } },
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { scope.launch { scaffoldState.bottomSheetState.expand() } },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            if (music.name.isEmpty()) {
                                Text(text = isExpanded.toString())
                            } else {
                                Text(text = music.name)
                            }

                        }

                    }


                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Hello")
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
        sheetShape = bottomSheetShape,
    )

}

