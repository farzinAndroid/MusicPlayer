package com.farzin.musicplayer.viewmodels

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.musicplayer.data.model.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMusicViewModel @Inject constructor() : ViewModel() {

    val musicList = MutableStateFlow<List<Music>>(emptyList())


    fun getAllMusic(context: Context) {

        val contentResolver = context.contentResolver

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }


        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
        )

        // Display music in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val query = contentResolver.query(
            collection,
            projection,
            null,
            null, // No selection arguments
            sortOrder
        )

        val musicTemp = mutableListOf<Music>()

        viewModelScope.launch(Dispatchers.IO) {
            query?.use { cursor ->

                if (cursor.moveToFirst()) {
                    do {
                        // Cache column indices.
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                        val albumIdColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                        val nameColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                        val durationColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                        val albumNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                        val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)



                        // Get values of columns for a given video.
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn)
                        val duration = cursor.getInt(durationColumn)
                        val album = cursor.getString(albumNameColumn)
                        val albumId = cursor.getLong(albumIdColumn)
                        val path = cursor.getString(dataColumn)
                        val uri = Uri.parse("content://media/external/audio/albumart")
                        val artUri = Uri.withAppendedPath(uri, albumId.toString()).toString()

                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        // Stores column values and the contentUri in a local object
                        // that represents the media file.
                        musicTemp += Music(id, name, album, duration, contentUri, artUri)
                    }while (cursor.moveToNext())

                } else {
                    // Handle the case where no results are found
                    Log.e("TAG", "No music found")
                }

            }
            query?.close()

            musicList.emit(musicTemp)
        }
    }

}