package com.farzin.musicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.farzin.musicplayer.nav_graph.SetupNavGraph
import com.farzin.musicplayer.ui.components.ChangeStatusBarColor
import com.farzin.musicplayer.ui.theme.MusicPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT) { resources ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    resources.configuration.isNightModeActive
                } else {
                    (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_YES
                }
            },
            navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT) { resources ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    resources.configuration.isNightModeActive
                } else {
                    (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_YES
                }
            }
        )
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    checkPermissions()

                    val navController = rememberNavController()
                    ChangeStatusBarColor()

                    SetupNavGraph(navController = navController)
                }
            }
        }
    }

    private fun checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_AUDIO,
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    1
                )
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
        }
    }
}
