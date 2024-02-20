package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.farzin.musicplayer.R
import com.farzin.musicplayer.data.model.Music
import com.farzin.musicplayer.data.model.TabItem
import com.farzin.musicplayer.ui.theme.darkText

@Composable
fun MainScreenTabLayout(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(),
    onSongSelected:(Music)->Unit
) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf<TabItem>(
        TabItem(
            text = stringResource(R.string.songs),
            textColor = MaterialTheme.colorScheme.darkText
        ),
        TabItem(
            text = stringResource(R.string.albums),
            textColor = MaterialTheme.colorScheme.darkText
        ),
        TabItem(
            text = stringResource(R.string.artists),
            textColor = MaterialTheme.colorScheme.darkText
        )
    )

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            tabs = {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tab.text,
                                color = tab.textColor,
                                fontWeight = FontWeight.Bold
                            )
                        },
                    )
                }
            },
            divider = {},
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent
        )


        when(selectedTabIndex){
            0->{
                AllSongs(
                    navController = navController,
                    paddingValues = paddingValues,
                    onSongSelected = {
                        onSongSelected(it)
                    }
                )
            }
            1->{
                Text(text = "album")
            }
            2->{
                Text(text = "artist")
            }
        }
    }
}