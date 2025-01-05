package io.eclypse.bitmapcomposer.note

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density

/**
 * Simple class that carries the information needed to render a screenshot from
 * any Composable.
 */
data class Screenshot (
    val currentActivity: Activity,
    val screenDensity: Density,
    val composableView: @Composable () -> Unit
)