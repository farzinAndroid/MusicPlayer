package com.farzin.musicplayer.data.model

import android.net.Uri

data class Music(
    val id:Long,
    val name:String,
    val albumName:String,
    val duration:Int,
    val contentUri:Uri,
    val thumbnail:String,
)
