package com.farzin.musicplayer.ui.screens.all_music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.ui.screens.components.SearchBarSection
import com.farzin.musicplayer.ui.theme.mainBackground

@Composable
fun TopSearchSection(
    onMenuClicked:()->Unit,
    onCardClicked:()->Unit
) {

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .fillMaxHeight(0.10f),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { onMenuClicked() }) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription ="",
                modifier = Modifier.size(16.dp).weight(0.1f)
            )
        }

        SearchBarSection(
            modifier = Modifier
                .weight(0.9f)
                .height(34.dp)
                .clickable { onCardClicked() }
        )
    }
}