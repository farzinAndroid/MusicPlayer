package com.farzin.musicplayer.data.model

data class MusicAlbum(
    val albumId:Long = 0L,
    val albumName:String  = "",
    val numberOfSongs:Int  = 0,
    val albumArt:String  = "",
    val artistName:String  = ""
)
