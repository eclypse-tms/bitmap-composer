package io.eclypse.bitmapcomposer.note

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import coil3.compose.AsyncImage

/**
 * We have 3 different ways of displaying images in the [NoteScreen].
 * 1) [ByUrl]: It contains the URL where the image is saved in the local
 * disk.
 * 2) [ByBitmap] is used for screenshots. It contains the actual image instead of
 * file reference to the image.
 * 3) [ByDrawable] is used for testing and previews. It contains the drawable ID in
 * the res folder.
 */
sealed class ImageReference {
    /**
     * Represents an attachment that is obtainable by its URL. When the user is interacting with the
     * app, we use this mode and load the images asynchronously using [AsyncImage].
     */
    data class ByUrl(val url: Uri) : ImageReference()

    /**
     * Represents an attachment that is obtainable by its bitmap. This mode is only
     * used for recording a screenshot of the note.
     */
    data class ByBitmap(val bitmap: ImageBitmap) : ImageReference()

    /**
     * Represents an attachment that is obtainable by its drawable ID. This mode is only
     * used for previews.
     */
    data class ByDrawable(@DrawableRes val drawableId: Int) : ImageReference()
}

