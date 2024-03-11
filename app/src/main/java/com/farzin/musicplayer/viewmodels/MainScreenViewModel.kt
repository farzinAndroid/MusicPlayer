package com.farzin.musicplayer.viewmodels

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.player.service.PlayerEvent
import com.farzin.musicplayer.data.player.service.SongService
import com.farzin.musicplayer.data.player.service.SongServiceHandler
import com.farzin.musicplayer.data.player.service.SongState
import com.farzin.musicplayer.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val songServiceHandler: SongServiceHandler
) : ViewModel() {

    val musicList = MutableStateFlow<List<Music>>(emptyList())
    val duration = MutableStateFlow(0L)
    val sliderProgress = MutableStateFlow(0f)
    val songProgress = MutableStateFlow(0L)
    val isPlaying = MutableStateFlow(false)
    val currentSelectedSong = MutableStateFlow<Music?>(null)



    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        getAllMusic()
    }
    init {
        viewModelScope.launch {
            songServiceHandler.songState.collectLatest { songState->
                when(songState){
                    SongState.Initial -> _uiState.value = UIState.Initial
                    is SongState.Buffering -> calculateProgressValue(songState.progress)
                    is SongState.Ready -> {
                        _uiState.value = UIState.Ready
                        duration.emit(songState.duration)
                    }
                    is SongState.Playing -> isPlaying.emit(songState.isPlaying)
                    is SongState.Progress ->{
                        calculateProgressValue(songState.progress)
                        songProgress.emit(songState.progress)
                    }
                    is SongState.CurrentPlayingSong -> {
                        currentSelectedSong.emit(musicList.value[songState.mediaItemIndex])
                    }
                }
            }
        }

    }

    private fun calculateProgressValue(currentProgress: Long) {
        viewModelScope.launch {
            if (currentProgress > 0){
                sliderProgress.emit((((currentProgress.toFloat()) / duration.value.toFloat()) * 100f))
            }else sliderProgress.emit(0f)
        }

    }



    private fun getAllMusic() {
        viewModelScope.launch {
            musicList.emit(songRepository.getAllSongs())
            setMediaItems()
        }
    }

    fun onUIEvent(
        uiEvents: UIEvents
    ){
        viewModelScope.launch {
            when(uiEvents) {
                UIEvents.PlayPause -> songServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)
                is UIEvents.SeekTo -> {
                    songServiceHandler.onPlayerEvents(
                        PlayerEvent.SeekTo,
                        seekPosition = ((duration.value * uiEvents.position) / 100f).toLong()
                    )
                }
                UIEvents.SeekToNext -> songServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
                UIEvents.SeekToPrevious -> songServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrevious)
                is UIEvents.SelectedSongChange -> {
                    songServiceHandler.onPlayerEvents(
                        PlayerEvent.SongChange,
                        selectedSongIndex = uiEvents.index
                    )
                }
                is UIEvents.UpdateProgress -> {
                    songServiceHandler.onPlayerEvents(PlayerEvent.UpdateProgress(uiEvents.newProgress))
                    sliderProgress.emit(uiEvents.newProgress)
                }
            }
        }
    }

    private fun setMediaItems(){
        musicList.value.map {
            MediaItem.Builder()
                .setUri(it.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist(it.artist)
                        .setAlbumTitle(it.title)
                        .setDisplayTitle(it.displayName)
                        .build()
                )
                .build()

        }.also {
            songServiceHandler.setMediaItems(it)
        }
    }


    fun startService(context: Context){
        val intent = Intent(context,SongService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        }else{
            context.startService(intent)
        }
    }



    override fun onCleared() {
        viewModelScope.launch {
            songServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onCleared()
    }



}

sealed class UIEvents {
    data object PlayPause : UIEvents()
    data class SelectedSongChange(val index: Int) : UIEvents()
    data object SeekToNext : UIEvents()
    data object SeekToPrevious : UIEvents()
    data class SeekTo(val position: Float) : UIEvents()
    data class UpdateProgress(val newProgress: Float) : UIEvents()
}

sealed class UIState{
    data object Initial:UIState()
    data object Ready:UIState()
}

