package io.eclypse.bitmapcomposer.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density

data class Screenshot (
    val currentActivity: Activity,
    val screenDensity: Density,
    val composableView: @Composable () -> Unit
)