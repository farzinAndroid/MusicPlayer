package com.farzin.musicplayer.viewmodels

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.musics.SongAmplitudeHelper
import com.farzin.musicplayer.data.player.service.PlayerEvent
import com.farzin.musicplayer.data.player.service.SongService
import com.farzin.musicplayer.data.player.service.SongServiceHandler
import com.farzin.musicplayer.data.player.service.SongState
import com.farzin.musicplayer.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val songServiceHandler: SongServiceHandler,
    private val songAmplitudeHelper: SongAmplitudeHelper
) : ViewModel() {

    val musicListDateAddedDesc = MutableStateFlow<List<Music>>(emptyList())
    val musicListDateAddedAsc = MutableStateFlow<List<Music>>(emptyList())
    val musicListNameDesc = MutableStateFlow<List<Music>>(emptyList())
    val musicListNameAsc = MutableStateFlow<List<Music>>(emptyList())
    val amplitudes = MutableStateFlow<List<Int>>(emptyList())
    val duration = MutableStateFlow(0L)
    val sliderProgress = MutableStateFlow(0f)
    val songProgress = MutableStateFlow(0L)
    val isPlaying = MutableStateFlow(false)
    val currentSelectedSong = MutableStateFlow<Music?>(null)
    val sort = MutableStateFlow(0)



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
                        when(sort.value){
                            1->{
                                currentSelectedSong.emit(musicListDateAddedDesc.value[songState.mediaItemIndex])
                                getSongAmplitudes(musicListDateAddedDesc.value[songState.mediaItemIndex].path)
                            }
                            2->{
                                currentSelectedSong.emit(musicListDateAddedAsc.value[songState.mediaItemIndex])
                                getSongAmplitudes(musicListDateAddedAsc.value[songState.mediaItemIndex].path)
                            }
                            3->{
                                currentSelectedSong.emit(musicListNameDesc.value[songState.mediaItemIndex])
                                getSongAmplitudes(musicListNameDesc.value[songState.mediaItemIndex].path)
                            }
                            4->{
                                currentSelectedSong.emit(musicListNameAsc.value[songState.mediaItemIndex])
                                getSongAmplitudes(musicListNameAsc.value[songState.mediaItemIndex].path)
                            }
                        }

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



    fun getAllMusic() {
        viewModelScope.launch {
            musicListDateAddedDesc.emit(songRepository.getAllMusicDateDesc())
            musicListDateAddedAsc.emit(songRepository.getAllMusicDateAsc())
            musicListNameDesc.emit(songRepository.getAllMusicNameDesc())
            musicListNameAsc.emit(songRepository.getAllMusicNameAsc())

            when(sort.value){
                1->setMediaItems(musicListDateAddedDesc.value)
                2->setMediaItems(musicListDateAddedAsc.value)
                3->setMediaItems(musicListNameDesc.value)
                4->setMediaItems(musicListNameAsc.value)
            }
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
                UIEvents.SeekToNext -> {
                    songServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
                    getSongAmplitudes(currentSelectedSong.value?.path!!)
                }
                UIEvents.SeekToPrevious -> songServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrevious)
                is UIEvents.SelectedSongChange -> {
                    when(sort.value){
                        1->{
                            getSongAmplitudes(musicListDateAddedDesc.value[uiEvents.index].path)
                            currentSelectedSong.value = musicListDateAddedDesc.value[uiEvents.index]
                        }
                        2->{
                            getSongAmplitudes(musicListDateAddedAsc.value[uiEvents.index].path)
                            currentSelectedSong.value = musicListDateAddedAsc.value[uiEvents.index]
                        }
                        3->{
                            getSongAmplitudes(musicListNameDesc.value[uiEvents.index].path)
                            currentSelectedSong.value = musicListNameDesc.value[uiEvents.index]
                        }
                        4->{
                            getSongAmplitudes(musicListNameAsc.value[uiEvents.index].path)
                            currentSelectedSong.value = musicListNameAsc.value[uiEvents.index]
                        }
                    }

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

    fun setMediaItems(musicList:List<Music>){
        musicList.map {
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
            Log.e("TAG","media items : ${it}")
        }
    }

    private fun getSongAmplitudes(path:String){
        viewModelScope.launch(Dispatchers.IO) {
            amplitudes.emit(songAmplitudeHelper.getSongAmplitudes(path))
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


    fun applySort(songSort:Int){
        viewModelScope.launch {
            sort.emit(songSort)
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

