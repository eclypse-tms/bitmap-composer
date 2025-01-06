package io.eclypse.bitmapcomposer.note

import android.app.Activity
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.drawToBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.math.roundToInt
/**
 * Draws an arbitrary composable into a bitmap
 */
class BitmapComposer (
    private val mainScope: CoroutineScope,
) {
    /**
     * Renders an arbitrary Composable View into a Bitmap.
     *
     * @param activity The host activity that is needed to attach the composable content to the view hierarchy.
     * @param width The width of the bitmap in points (not pixels).
     * @param height Optional height of the bitmap in points (not pixels). If height is not provided,
     * the content will calculate its fitting size based on the width provided
     * @param screenDensity screen density to interpret the width and height.
     * @param content An arbitrary composable content to render.
     * @return A Bitmap representing the rendered Composable content.
     */
    suspend fun composableToBitmap(
        activity: Activity,
        width: Dp,
        height: Dp? = null,
        screenDensity: Density,
        content: @Composable () -> Unit
    ): Bitmap = suspendCancellableCoroutine { continuation ->
        mainScope.launch {
            // Step 0: Interpret the pixels
            val contentWidthInPixels = (screenDensity.density * width.value).roundToInt()
            val contentHeightInPixels =
                (screenDensity.density * (height ?: 3000.dp).value).roundToInt()

            // Step 1: Create a container to hold the ComposeView temporarily
            val container = FrameLayout(activity).apply {
                layoutParams =
                    ViewGroup.LayoutParams(contentWidthInPixels, contentHeightInPixels)
                visibility = View.INVISIBLE // Keep it invisible
            }

            // Step 2: Create and configure the ComposeView
            val composeView = ComposeView(activity).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                setContent { content() }
            }

            container.addView(composeView)

            // Step 3: Attach container to the root decor view
            val decorView = activity.window.decorView as ViewGroup
            decorView.addView(container)

            val heightMeasureSpecifications = if (height == null) {
                View.MeasureSpec.AT_MOST
            } else {
                View.MeasureSpec.EXACTLY
            }

            // Step 4: Wait for the ComposeView to be drawn and capture the bitmap
            Handler(Looper.getMainLooper()).post {
                container.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        contentWidthInPixels,
                        View.MeasureSpec.EXACTLY
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        contentHeightInPixels,
                        heightMeasureSpecifications
                    )
                )

                container.layout(0, 0, contentWidthInPixels, contentHeightInPixels)

                val bitmap = composeView.drawToBitmap()
                continuation.resume(bitmap)

                // Step 5: Clean up - remove the container
                decorView.removeView(container)
            }
        }
    }
}