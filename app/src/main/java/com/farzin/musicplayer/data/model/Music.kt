package com.farzin.musicplayer.data.model

import android.net.Uri

data class Music(
    val id:Long = 0L,
    val name:String = "",
    val albumName:String = "",
    val duration:Int = 0,
    val contentUri:Uri? = null,
    val thumbnail:String = "",
)
