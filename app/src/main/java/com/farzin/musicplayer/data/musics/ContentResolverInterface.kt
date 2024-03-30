package com.farzin.musicplayer.data.musics

import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.model.MusicAlbum

interface ContentResolverInterface {
    // getting all songs
    suspend fun getAllMusicDateDesc() : List<Music>
    suspend fun getAllMusicDateAsc() : List<Music>
    suspend fun getAllMusicNameDesc() : List<Music>
    suspend fun getAllMusicNameAsc() : List<Music>
    // getting all songs

    // getting all albums
    suspend fun getAllAlbums():List<MusicAlbum>
    suspend fun getAllSongsBasedOnAlbum(id:Long):List<Music>
}