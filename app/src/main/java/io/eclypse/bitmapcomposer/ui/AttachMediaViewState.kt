package io.eclypse.bitmapcomposer.ui

import android.net.Uri
import java.io.File
import java.util.UUID

/**
 * An ephemeral view state that is used while taking a photo or picking an existing picture from
 * the gallery
 */
data class AttachMediaViewState(
    /**
     *  to determine if the user has requested to attach media to the note
     */
    val requestedToAttachMedia: Boolean = false,

    /**
     * Temporary file in the device's cache folder that contains the image from the photo.
     * Use this file reference to convert the file to an actual Bitmap.
     */
    val takePhotoFile: File? = null,

    /**
     * an ephemeral state that points to the sandboxed Url of the temporary file that is needed
     * for the Camera API.
     * Do not use this reference to reconstitute a file as the sandbox url is not a real location
     * in the device.
     */
    val takePhotoSandboxedUrl: Uri? = null,

    /**
     * this property is only filled after the user wants to take a photo or pick an image
     * but the number of attachment limit has already been reached
     */
    val attachmentLimitHasBeenReached: UUID? = null,
)