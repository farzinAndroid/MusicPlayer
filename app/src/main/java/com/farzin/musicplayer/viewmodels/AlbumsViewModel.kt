package com.farzin.musicplayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.model.MusicAlbum
import com.farzin.musicplayer.data.musics.ContentResolverHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper
):ViewModel() {


    val allAlbumsDateDesc = MutableStateFlow<List<MusicAlbum>>(emptyList())
    val allSongsBasedOnAlbum = MutableStateFlow<List<Music>>(emptyList())


    init {
        getAllAlbums()
    }

    fun getAllAlbums(){
        viewModelScope.launch {
            allAlbumsDateDesc.emit(contentResolverHelper.getAllAlbumsDateDesc())
        }
    }

    fun getSongsBasedOnAlbum(albumId:Long){
        viewModelScope.launch {
            allSongsBasedOnAlbum.emit(contentResolverHelper.getAllSongsBasedOnAlbum(albumId))
        }
    }


}