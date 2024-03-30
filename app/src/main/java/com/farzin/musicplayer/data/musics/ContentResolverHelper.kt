package com.farzin.musicplayer.data.musics

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.model.MusicAlbum
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(
    @ApplicationContext private val context: Context,
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


    // getting all songs
    override suspend fun getAllMusicDateDesc(): List<Music> {
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


        }

        return musicTemp

    }

    override suspend fun getAllMusicDateAsc(): List<Music> {
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
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} ASC"



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


        }

        return musicTemp
    }

    override suspend fun getAllMusicNameDesc(): List<Music> {
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
        val sortOrder = "${MediaStore.Audio.Media.TITLE} DESC"



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


        }

        return musicTemp
    }

    override suspend fun getAllMusicNameAsc(): List<Music> {
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
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"



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


        }

        return musicTemp
    }


    // getting all songs


    // getting all albums

    override suspend fun getAllAlbumsDateDesc(): List<MusicAlbum> {
        val albumSet = linkedSetOf<MusicAlbum>()

        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
            MediaStore.Audio.AudioColumns.ARTIST,
        )

        // Sort albums by name
        val sortOrder = "${MediaStore.Audio.Media.ALBUM} DESC"

        cursor = contentResolver.query(
            collection,
            projection,
            null, // No selection clause
            null, // No selection arguments
            sortOrder
        )

        withContext(Dispatchers.IO) {
            cursor?.use { mcursor ->
                val idColumn = mcursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID)
                val nameColumn = mcursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM)
                val artistColumn = mcursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)

                while (mcursor.moveToNext()) {
                    // Get values of columns for a given album.
                    val id = mcursor.getLong(idColumn)
                    val name = mcursor.getString(nameColumn)
                    val artist = mcursor.getString(artistColumn)
                    val artUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id).toString()

                    // Create a MusicAlbum object
                    val album = MusicAlbum(
                        albumName = name,
                        albumId = id,
                        albumArt = artUri,
                        numberOfSongs = -1 // As we don't have the actual number, set to -1 or query it separately
                    )

                    // Add to the set, duplicates will be ignored
                    albumSet += album
                }

                if (albumSet.isEmpty()) {
                    // Handle the case where no albums are found
                    Log.e("TAG", "No album found")
                }
            }
        }

        // Convert the set to a list and return it
        return albumSet.toList()
    }

    override suspend fun getAllSongsBasedOnAlbum(id: Long): List<Music> {
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
        val sortOrder = "${MediaStore.Audio.Media.ALBUM_ID} =?"
        val selectionArgs = arrayOf(id.toString())

        // Correct the selection and use the selectionArgs to filter by album ID
        val selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?"



        cursor = contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
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


        }

        return musicTemp
    }

}