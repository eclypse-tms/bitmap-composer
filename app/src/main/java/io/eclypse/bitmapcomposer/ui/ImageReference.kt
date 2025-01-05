package io.eclypse.bitmapcomposer.ui

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap

/**
 * We have 3 different ways of displaying attachments in the [AddNoteScreen]. [ByUrl]
 * is used in production. It only contains the URL where the attachment is saved in the local
 * disk. [ByBitmap] is used for screenshots. It contains the actual image instead of
 * file reference to the image. This is because [AsyncImage] from Coil 3rd party library does
 * not work when rendering screenshots on a file.
 * [ByDrawable] is used for testing and previews. It contains the drawable ID.
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

