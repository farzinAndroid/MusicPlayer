package com.farzin.musicplayer.data.model

data class MusicAlbum(
    val albumId:Long,
    val albumName:String,
    val numberOfSongs:Int,
    val albumArt:String,
    val artistName:String
)
