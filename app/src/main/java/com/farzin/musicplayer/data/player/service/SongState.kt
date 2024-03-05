package com.farzin.musicplayer.data.player.service

sealed class SongState {
    data object Initial : SongState()
    data class Buffering(val progress: Long) : SongState()
    data class Ready(val duration: Long): SongState()
    data class Progress(val progress: Long): SongState()
    data class Playing(val isPlaying:Boolean): SongState()
    data class CurrentPlayingSong(val mediaItemIndex:Int): SongState()

}