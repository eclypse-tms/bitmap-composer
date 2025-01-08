package io.eclypse.bitmapcomposer.note

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Converts a Uri pointing to an image on disk into a Bitmap.
 *
 * @param uri The Uri of the image.
 * @return A Bitmap of the image, or null if the conversion fails.
 */
fun Context.uriToBitmap(uri: Uri): ImageBitmap? {
    return try {
        // Open an InputStream for the Uri
        val inputStream = this.contentResolver.openInputStream(uri)
        // Decode the InputStream into a Bitmap
        BitmapFactory.decodeStream(inputStream)
            .also {
                inputStream?.close() // Close the stream after decoding
            }.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if an error occurs
    }
}