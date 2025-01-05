package io.eclypse.bitmapcomposer.note

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun NoteCarouselItem(attachment: ImageReference) {
    when (attachment) {
            is ImageReference.ByUrl -> {
                val imageUrl: Uri = attachment.url

                val imagePainter: AsyncImagePainter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build()
                )
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                )
            }

            is ImageReference.ByDrawable -> {
                Image(
                    painter = painterResource(id = attachment.drawableId),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                )
            }

            is ImageReference.ByBitmap -> {
                Image(
                    bitmap = attachment.bitmap,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                )
            }
        }
}