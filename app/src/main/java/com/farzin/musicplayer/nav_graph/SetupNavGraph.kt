package com.farzin.musicplayer.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farzin.musicplayer.ui.screens.main_screen.MainScreen
import com.farzin.musicplayer.ui.screens.search_screen.SearchScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = Screens.AllMusic.route
    ){

        composable(Screens.AllMusic.route){
            MainScreen(navController = navController)
        }

        composable(Screens.Search.route){
            SearchScreen(navController = navController)
        }
    }

}