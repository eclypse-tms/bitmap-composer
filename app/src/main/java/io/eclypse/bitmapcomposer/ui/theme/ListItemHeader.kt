package io.eclypse.bitmapcomposer.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ListItemHeader(text: String, fontSize: TextUnit = TextUnit.Unspecified) {
    var fontSizeToUse: TextUnit = 14.sp
    if (fontSize == TextUnit.Unspecified) {
        fontSizeToUse = fontSize
    }

    Text(text = text,
        color = secondaryText,
        fontSize = fontSizeToUse)
}

@Composable
fun ListItemHeader(@StringRes id: Int, fontSize: TextUnit = TextUnit.Unspecified) {
    var fontSizeToUse: TextUnit = 14.sp
    if (fontSize == TextUnit.Unspecified) {
        fontSizeToUse = fontSize
    }

    Text(text = stringResource(id = id),
        color = secondaryText,
        fontSize = fontSizeToUse)
}

@Preview
@Composable
fun PreviewListItemHeader() {
    Column {
        ListItemHeader("Header")
        ListItemType1(itemId = 34, headlineText = "Field Name",
            trailingText = "East 40")
    }
}