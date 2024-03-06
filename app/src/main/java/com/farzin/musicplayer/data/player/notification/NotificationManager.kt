package com.farzin.musicplayer.data.player.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.farzin.musicplayer.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exoPlayer: ExoPlayer,
) {

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @UnstableApi
    fun startNotification(
        mediaSession: MediaSession,
        mediaSessionService: MediaSessionService
    ){
        buildNotification(mediaSession)
        startForeGroundService(mediaSessionService)
    }


    private fun startForeGroundService(mediaSessionService: MediaSessionService){
        var notification:Notification?=null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification=Notification.Builder(context,Constants.NOTIFICATION_CHANNEL_ID)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            mediaSessionService.startForeground(Constants.NOTIFICATION_ID,notification)

        } else {
            notification=Notification.Builder(context)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            mediaSessionService.startForeground(Constants.NOTIFICATION_ID,notification)
        }

    }


    @UnstableApi
    private fun buildNotification(mediaSession:MediaSession) {
        PlayerNotificationManager.Builder(
            context,
            Constants.NOTIFICATION_ID,
            Constants.NOTIFICATION_CHANNEL_ID
        )
            .setMediaDescriptionAdapter(
                MediaNotificationAdapter(
                    context,
                    mediaSession.sessionActivity
                )
            )
            .build()
            .also {
                it.setMediaSessionToken(mediaSession.sessionCompatToken)
                it.setPriority(NotificationCompat.PRIORITY_LOW)
                it.setUseNextActionInCompactView(true)
                it.setUsePreviousActionInCompactView(true)
                it.setUsePlayPauseActions(true)
                it.setUseStopAction(true)
                it.setPlayer(exoPlayer)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            Constants.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}