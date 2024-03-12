package com.farzin.musicplayer.data.model



data class Music(
    val uri: String = "",
    val displayName:String = "",
    val id:Long = 0L,
    val artist:String = "",
    val path:String = "",
    val duration:Int = 0,
    val title:String = "",
    val albumArt:String = "",
)
