package com.farzin.musicplayer.data.player.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {

    private val job: Job? = null

    private val _songState = MutableStateFlow<SongState>(SongState.Initial)
    val songState = _songState.asStateFlow()

    init {
        exoPlayer.addListener(this)
    }

    // MY FUNS//////////////////////////////////////////////

    fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun setMediaItems(mediaItem: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItem)
        exoPlayer.prepare()
    }

    suspend fun onPlayerEvents(
        playerEvent: PlayerEvent,
        seekPosition: Long = 0L,
        selectedSongIndex: Int = -1,
    ) {
        when (playerEvent) {
            PlayerEvent.PlayPause -> playOrPause()
            PlayerEvent.SeekTo -> exoPlayer.seekTo(seekPosition)
            PlayerEvent.SeekToNext -> exoPlayer.seekToNext()
            PlayerEvent.SeekToPrevious -> exoPlayer.seekToPrevious()
            PlayerEvent.SongChange -> {
                when(selectedSongIndex){
                    exoPlayer.currentMediaItemIndex->{
                        playOrPause()
                    }
                    else->{
                        exoPlayer.seekToDefaultPosition(selectedSongIndex)
                        _songState.value = SongState.Playing(true)
                        exoPlayer.playWhenReady = true
                        startProgressUpdate()
                    }
                }
            }
            PlayerEvent.Stop -> {
                stopProgressUpdate()
                exoPlayer.stop()
                exoPlayer.release()
            }
            is PlayerEvent.UpdateProgress -> {
                exoPlayer.seekTo((exoPlayer.duration * playerEvent.newProgress).toLong())
            }
        }
    }

    private suspend fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _songState.value = SongState.Playing(true)
            startProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _songState.value = SongState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _songState.value = SongState.Playing(false)
    }


    // OVERRIDE FUNS//////////////////////////////////
    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when(playbackState){
            ExoPlayer.STATE_BUFFERING->{
                _songState.value = SongState.Buffering(exoPlayer.currentPosition)
            }
            ExoPlayer.STATE_READY->{
                _songState.value = SongState.Ready(exoPlayer.duration)
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _songState.value = SongState.Playing(isPlaying)
        _songState.value = SongState.CurrentPlayingSong(exoPlayer.currentMediaItemIndex)
        super.onIsPlayingChanged(isPlaying)
        if (isPlaying){
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        }else{
            stopProgressUpdate()
        }
    }


}