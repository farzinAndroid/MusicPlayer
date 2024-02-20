package com.farzin.musicplayer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farzin.musicplayer.R
import com.farzin.musicplayer.ui.theme.searchBarColor

@Composable
fun SearchBarSection(
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = modifier,
        shape = Shapes().large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.searchBarColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp)
            )

            Text(
                text = stringResource(R.string.searchSongs),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(.9f).padding(start = 4.dp)
            )

        }

    }

}