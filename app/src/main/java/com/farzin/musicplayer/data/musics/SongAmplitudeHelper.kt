package com.farzin.musicplayer.data.musics

import linc.com.amplituda.Amplituda
import javax.inject.Inject


class SongAmplitudeHelper @Inject constructor(
    private val amplituda: Amplituda,
) {

    fun getSongAmplitudes(path: String) : List<Int> {

        var amplitudeList = emptyList<Int>()
        val processAudio = amplituda.processAudio(path)
        processAudio[
            { result ->
                amplitudeList = result.amplitudesAsList()
            },
            { exception ->
                println(exception.message)
            }
        ]
        return amplitudeList
    }

}