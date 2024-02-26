package com.farzin.musicplayer.repository

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class PlayerRepository
@Inject constructor(private val exoPlayer: ExoPlayer) {


    fun playMusic(mediaSource: MediaItem?){
        // Stop any currently playing music
        exoPlayer.stop()

        // Set the new MediaSource to ExoPlayer
        if (mediaSource != null) {
            exoPlayer.setMediaItem(mediaSource)
            exoPlayer.prepare()
            exoPlayer.play() // Start playing the new music
        }
    }

}