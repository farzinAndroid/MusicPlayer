package com.farzin.musicplayer.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farzin.musicplayer.ui.screens.album_screen.AlbumScreen
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

        composable(
            Screens.Album.route + "?albumId={albumId}",
            arguments = listOf(
                navArgument("albumId"){
                    defaultValue = 0L
                    type = NavType.LongType
                }
            )
        ){
            it.arguments?.getLong("albumId")?.let {albumId->
                AlbumScreen(
                    albumId =albumId,
                    navController=navController
                )
            }

        }
    }

}