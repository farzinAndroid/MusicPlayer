package com.farzin.musicplayer.repository

import com.farzin.musicplayer.data.musics.ContentResolverHelper
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper
) {

    suspend fun getAllMusicDateDesc() = contentResolverHelper.getAllMusicDateDesc()
    suspend fun getAllMusicDateAsc() = contentResolverHelper.getAllMusicDateAsc()
    suspend fun getAllMusicNameDesc() = contentResolverHelper.getAllMusicNameDesc()
    suspend fun getAllMusicNameAsc() = contentResolverHelper.getAllMusicNameAsc()


}