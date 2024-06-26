package com.farzin.musicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)



val ColorScheme.searchBarColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF2B2B2B) else Color(0xFFEBEBEB)

val ColorScheme.mainBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF000000) else Color(0xFFFFFFFF)

val ColorScheme.darkText: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE7E7E7) else Color(0xFF000000)

val ColorScheme.likeColor: Color
    @Composable
    get() = Color(0xFFDD0000)

val ColorScheme.albumPlayColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF028A6E) else  Color(0xFF00D5A9)

val ColorScheme.lightGray: Color
    @Composable
    get() = Color(0xFF9E9E9E)