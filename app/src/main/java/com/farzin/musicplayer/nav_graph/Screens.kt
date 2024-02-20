package com.farzin.musicplayer.nav_graph

abstract class Screens(val route:String) {

    data object AllMusic : Screens(route = "all_music_screen")
    data object Search : Screens(route = "search_screen")
    data object FullScreen : Screens(route = "full_screen_screen")

}