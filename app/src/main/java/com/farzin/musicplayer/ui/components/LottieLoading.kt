package com.farzin.musicplayer.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farzin.musicplayer.R

@Composable
fun LottieLoading(modifier: Modifier = Modifier) {

    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    LottieAnimation(
        composition = lottie,
        modifier = modifier
    )

}