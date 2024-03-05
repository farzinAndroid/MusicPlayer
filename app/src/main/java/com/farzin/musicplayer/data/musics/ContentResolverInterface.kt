package com.farzin.musicplayer.data.musics

import com.farzin.musicplayer.data.model.Music

interface ContentResolverInterface {

    suspend fun getAllMusic() : List<Music>

}