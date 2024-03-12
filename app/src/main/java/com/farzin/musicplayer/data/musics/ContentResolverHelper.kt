package com.farzin.musicplayer.data.musics

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.farzin.musicplayer.data.model.Music
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val songAmplitudeHelper: SongAmplitudeHelper,
) : ContentResolverInterface {

    private var cursor: Cursor? = null

    private val contentResolver = context.contentResolver

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    /*private var selectionClause: String? =
        "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"
    private var selectionArg = arrayOf("1")*/

    override suspend fun getAllMusic(): List<Music> {
        val musicTemp = mutableListOf<Music>()

        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
        )

        // Display music based on DATE_ADDED
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        cursor = contentResolver.query(
            collection,
            projection,
            null,
            null, // No selection arguments
            sortOrder
        )

        withContext(Dispatchers.IO) {
            cursor?.use { mcursor ->

                if (mcursor.moveToFirst()) {
                    do {
                        // Cache column indices.
                        val idColumn = mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                        val albumIdColumn =
                            mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                        val nameColumn =
                            mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                        val durationColumn =
                            mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                        val dataColumn = mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                        val artistColumn =
                            mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                        val titleColumn =
                            mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)


                        // Get values of columns for a given video.
                        val id = mcursor.getLong(idColumn)
                        val displayName = mcursor.getString(nameColumn)
                        val duration = mcursor.getInt(durationColumn)
                        val albumId = mcursor.getLong(albumIdColumn)
                        val path = mcursor.getString(dataColumn)
                        val artist = mcursor.getString(artistColumn)
                        val title = mcursor.getString(titleColumn)
                        val uri = Uri.parse("content://media/external/audio/albumart")
                        val artUri = Uri.withAppendedPath(uri, albumId.toString()).toString()

                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )


                        // Stores column values and the contentUri in a local object
                        // that represents the media file.
                        musicTemp += Music(
                            contentUri.toString(),
                            displayName,
                            id,
                            artist,
                            path,
                            duration,
                            title,
                            artUri,
                        )
                    } while (mcursor.moveToNext())

                } else {
                    // Handle the case where no results are found
                    Log.e("TAG", "No music found")
                }

            }
            cursor?.close()

        }

        return musicTemp

    }

}