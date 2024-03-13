package com.farzin.musicplayer.ui.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.farzin.musicplayer.R
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.components.MySpacerWidth
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.mainBackground
import com.farzin.musicplayer.viewmodels.DataStoreViewModel
import com.farzin.musicplayer.viewmodels.MainScreenViewModel

@Composable
fun SortDialogContent(
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
) {


    val options = listOf(
        1,
        2,
        3,
        4,
    )


    var selectedOptions by when (dataStoreViewModel.getSort()) {
        1 -> {
            remember { mutableIntStateOf(options[0]) }
        }

        2 -> {
            remember { mutableIntStateOf(options[1]) }
        }

        3 -> {
            remember { mutableIntStateOf(options[2]) }
        }

        4 -> {
            remember { mutableIntStateOf(options[3]) }
        }

        else -> {
            remember { mutableIntStateOf(options[0]) }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.25f)
            .clip(Shapes().large)
            .background(MaterialTheme.colorScheme.mainBackground),
        horizontalAlignment = Alignment.Start
    ) {


        MySpacerHeight(height = 10.dp)

        Text(
            text = stringResource(R.string.songs),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.darkText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        MySpacerHeight(height = 6.dp)

        options.forEachIndexed { index, title ->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {


                RadioButton(
                    selected = (options[index] == selectedOptions),
                    onClick = {
                        selectedOptions = title
                        dataStoreViewModel.saveSort(title)
                    },
                )

                MySpacerWidth(width = 8.dp)

                Text(
                    text = title.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.darkText,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}