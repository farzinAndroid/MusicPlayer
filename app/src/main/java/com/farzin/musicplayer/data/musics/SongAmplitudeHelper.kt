package com.farzin.musicplayer.data.musics

import linc.com.amplituda.Amplituda
import javax.inject.Inject


class SongAmplitudeHelper @Inject constructor(
    private val amplituda: Amplituda,
) {

    fun getSongAmplitudes(path: String) : List<Int> {

        /*amplituda.processAudio("/storage/emulated/0/Music/Linc - Amplituda.mp3")[
            { result: AmplitudaResult<String?> ->
            val amplitudesData = result.amplitudesAsList()
            val amplitudesForFirstSecond =
                result.amplitudesForSecond(1)
            val duration = result.getAudioDuration(AmplitudaResult.DurationUnit.SECONDS)
            val source = result.audioSource
            val sourceType = result.inputAudioType
        }, { exception: AmplitudaException? ->
            if (exception is AmplitudaIOException) {
                println("IO Exception!")
            }
        }
        ]*/

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