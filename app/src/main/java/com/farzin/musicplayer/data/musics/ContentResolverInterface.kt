package com.farzin.musicplayer.data.musics

import com.farzin.musicplayer.data.model.Music

interface ContentResolverInterface {

    suspend fun getAllMusicDateDesc() : List<Music>
    suspend fun getAllMusicDateAsc() : List<Music>
    suspend fun getAllMusicNameDesc() : List<Music>
    suspend fun getAllMusicNameAsc() : List<Music>
}