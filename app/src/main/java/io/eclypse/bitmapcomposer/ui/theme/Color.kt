package io.eclypse.bitmapcomposer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

//region Default Text
val defaultTextLight = Color(0xFF000000)
val defaultTextDark = Color(0xFFFFFFFF)

// region defaultText
val defaultText: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()) defaultTextDark else defaultTextLight
    }
//endregion

//region Secondary Text
val secondaryTextLight = Color(0xFF757575)
val secondaryTextDark = Color(0xFF757575)
val secondaryText: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()) secondaryTextDark else secondaryTextLight
    }
//endregion

//region Primary Background
val primaryBackgroundLight = Color(0xFFFFFFFF)
val primaryBackgroundDark = Color(0xFF212121)
val primaryBackground: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()) primaryBackgroundDark else primaryBackgroundLight
    }
//endregion

//region Default Icon
val defaultIconLight = Color(0xFF212121)
val defaultIconDark = Color(0xFF212121)

val defaultIcon: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()) defaultIconDark else defaultIconLight
    }
//endregion