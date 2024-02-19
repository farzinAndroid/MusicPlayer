package com.farzin.musicplayer.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farzin.musicplayer.ui.screens.all_music.AllMusicScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = Screens.AllMusic.route
    ){

        composable(Screens.AllMusic.route){
            AllMusicScreen(navController = navController)
        }

    }

}