package com.farzin.musicplayer.data.player.service

sealed class PlayerEvent {

    data object PlayPause : PlayerEvent()
    data object SongChange : PlayerEvent()
    data object SeekToNext : PlayerEvent()
    data object SeekToPrevious : PlayerEvent()
    data object SeekTo : PlayerEvent()
    data object Stop : PlayerEvent()
    data class UpdateProgress(val newProgress:Float) : PlayerEvent()

}