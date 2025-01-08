package io.eclypse.bitmapcomposer.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import io.eclypse.bitmapcomposer.R
import io.eclypse.bitmapcomposer.ui.theme.BitmapComposerTheme
import io.eclypse.bitmapcomposer.ui.theme.Purple40

@Composable
internal fun Thumbnail(
    modifier: Modifier = Modifier,
    index: Int,
    imageReference: ImageReference,
    isDeletable: Boolean = false,
    onDeleteAttachment: (Int) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.then(modifier),
    ) {
        val (closeButton, image) = createRefs()

        val deleteButtonSize: Dp = if (isDeletable) 20.dp else 0.dp
        val imageModifier = Modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(top = 8.dp, end = 8.dp)
            .constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

        IconButton(
            onClick = { onDeleteAttachment((index)) },
            colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = Purple40,
                contentColor = Color.White,
            ),
            modifier =
            Modifier
                .size(deleteButtonSize)
                .zIndex(2f)
                .constrainAs(closeButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
        ) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }

        when (imageReference) {
            is ImageReference.ByUrl -> {
                Image(
                    painter =
                    rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(imageReference.url)
                            .build(),
                    ),
                    contentDescription = "Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = imageModifier,
                )
            }

            is ImageReference.ByBitmap -> {
                Image(
                    bitmap = imageReference.bitmap,
                    contentDescription = "Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = imageModifier,
                )
            }

            is ImageReference.ByDrawable -> {
                Image(
                    painter = painterResource(id = imageReference.drawableId),
                    contentDescription = "Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = imageModifier,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewThumbnail() {
    BitmapComposerTheme {
        Thumbnail(
            modifier = Modifier.size(200.dp).padding(8.dp),
            index = 0,
            imageReference = ImageReference.ByDrawable(R.drawable.pleiades),
            isDeletable = true,
            onDeleteAttachment = { }
        )
    }
}