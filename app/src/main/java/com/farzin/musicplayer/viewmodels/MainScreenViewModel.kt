package com.farzin.musicplayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.repository.MainRepository
import com.farzin.musicplayer.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val musicList = MutableStateFlow<List<Music>>(emptyList())

    fun getAllMusic() {
        viewModelScope.launch {
            musicList.emit(mainRepository.getAllMusic())
        }
    }


    fun playMusic(mediaSource: MediaItem?){
        playerRepository.playMusic(mediaSource)
    }



}