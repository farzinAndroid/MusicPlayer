package com.farzin.musicplayer.repository

import com.farzin.musicplayer.data.model.Music

interface MainRepositoryInterface {

    suspend fun getAllMusic() : List<Music>

}