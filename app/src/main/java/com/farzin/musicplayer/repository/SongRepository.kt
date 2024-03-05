package com.farzin.musicplayer.repository

import com.farzin.musicplayer.data.musics.ContentResolverHelper
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper
) {

    suspend fun getAllSongs() = contentResolverHelper.getAllMusic()


}