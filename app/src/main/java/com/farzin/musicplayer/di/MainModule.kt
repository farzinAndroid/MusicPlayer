package com.farzin.musicplayer.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import linc.com.amplituda.Amplituda
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @UnstableApi
    @Provides
    @Singleton
    fun provideExoPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes
    ) =
        ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes,true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
            .build()


    @Provides
    @Singleton
    fun provideAudioAttributes()=
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_MEDIA)
            .build()


    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        exoPlayer: ExoPlayer
    ) = MediaSession
        .Builder(context,exoPlayer)
        .build()



    @Provides
    @Singleton
    fun provideAmplituda(
        @ApplicationContext context: Context,
    ) = Amplituda(context)

}