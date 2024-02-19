package com.farzin.musicplayer.nav_graph

abstract class Screens(val route:String) {

    data object AllMusic : Screens(route = "all_music_screen")

}