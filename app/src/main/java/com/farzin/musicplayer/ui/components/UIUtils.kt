package com.farzin.musicplayer.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.farzin.musicplayer.ui.theme.darkText
import com.farzin.musicplayer.ui.theme.mainBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MySpacerWidth(
    width: Dp,
    modifier: Modifier = Modifier
) {

    Spacer(modifier = modifier.width(width))

}

@Composable
fun MySpacerHeight(
    height: Dp,
    modifier: Modifier = Modifier
) {

    Spacer(modifier = modifier.height(height))

}

@Composable
fun ChangeStatusBarColor() {

    val systemUIController = rememberSystemUiController()

    systemUIController.setStatusBarColor(MaterialTheme.colorScheme.background)
    systemUIController.setSystemBarsColor(MaterialTheme.colorScheme.background)


}