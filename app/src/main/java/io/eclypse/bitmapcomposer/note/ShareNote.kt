package io.eclypse.bitmapcomposer.note

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShareNote (
    private val currentContext: Context
) {
    fun initiateWith(bitmap: ImageBitmap): Intent? {
        var shareIntent: Intent? = null

        try {
            val uri = saveImageToFile(
                currentContext,
                bitmap
            )
            if (uri != null) {
                shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/*"
                    flags = FLAG_ACTIVITY_NEW_TASK
                }
            }
        } catch (error: Throwable) {
            error.printStackTrace()
        }
        return shareIntent
    }

    private fun saveImageToFile(
        context: Context,
        imageBitmap: ImageBitmap
    ): Uri? {
        val tempCacheDirectory = context.cacheDir
        val currentTime = getCurrentTimestamp()
        val fileReferenceToImage = File(tempCacheDirectory, "Note-$currentTime.jpg")
        return try {
            val outputStream = FileOutputStream(fileReferenceToImage)
            imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            FileProvider.getUriForFile(context, "io.eclypse.bitmapcompose.provider", fileReferenceToImage)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}