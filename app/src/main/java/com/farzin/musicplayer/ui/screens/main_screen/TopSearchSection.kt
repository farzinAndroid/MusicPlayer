package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.R
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun TopSearchSection(
    onFilterClicked:()->Unit
) {

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .fillMaxHeight(0.10f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {


        Text(
            text = stringResource(R.string.music_player) ,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.darkText,
            modifier = Modifier.padding(start = 8.dp)
        )

        IconButton(onClick = { onFilterClicked() }) {
            Icon(
                imageVector = Icons.Rounded.FilterList,
                contentDescription ="",
                modifier = Modifier
                    .size(20.dp)
                    .weight(0.1f)
            )
        }

    }
}