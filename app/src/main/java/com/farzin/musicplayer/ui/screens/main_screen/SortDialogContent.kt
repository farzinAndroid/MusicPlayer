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
import com.farzin.musicplayer.data.model.SortModel
import com.farzin.musicplayer.ui.components.MySpacerHeight
import com.farzin.musicplayer.ui.components.MySpacerWidth
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.mainBackground
import com.farzin.musicplayer.viewmodels.DataStoreViewModel

@Composable
fun SortDialogContent(
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
) {


    val options = listOf(
        SortModel(
            title = stringResource(R.string.date_added_desc),
            sort = 1
        ),
        SortModel(
            title = stringResource(R.string.date_added_asc),
            sort = 2
        ),
        SortModel(
            title = stringResource(R.string.name_desc),
            sort = 3
        ),
        SortModel(
            title = stringResource(R.string.name_asc),
            sort = 4
        ),
    )


    var selectedOptions by when (dataStoreViewModel.getSort()) {
        1 -> {
            remember { mutableIntStateOf(options[0].sort) }
        }

        2 -> {
            remember { mutableIntStateOf(options[1].sort) }
        }

        3 -> {
            remember { mutableIntStateOf(options[2].sort) }
        }

        4 -> {
            remember { mutableIntStateOf(options[3].sort) }
        }

        else -> {
            remember { mutableIntStateOf(options[0].sort) }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight(0.30f)
            .clip(Shapes().large)
            .background(MaterialTheme.colorScheme.mainBackground),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {


        MySpacerHeight(height = 10.dp)

        Text(
            text = stringResource(R.string.sortby),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.darkText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        MySpacerHeight(height = 6.dp)

        options.forEachIndexed { index, sortModel ->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {


                RadioButton(
                    selected = (options[index].sort == selectedOptions),
                    onClick = {
                        selectedOptions = sortModel.sort
                        dataStoreViewModel.saveSort(sortModel.sort)
                    },
                )

                MySpacerWidth(width = 8.dp)

                Text(
                    text = sortModel.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.darkText,
                )
            }
        }
    }
}