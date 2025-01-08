package io.eclypse.bitmapcomposer.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * [ListItemType1] produces a row where the leading content is gray text and the trailing
 * content is black text that represents the selection. However, this behavior can be customized
 * with the emphasis parameter.
 * @param itemId an id that uniquely identifies this list from the others. Used in the action click callback.
 * @param headlineText gray colored text that appears on the left side of the row
 * @param modifier that gets applied to the [ListItem]
 * @param trailingText black colored text that appears on the right side of the row that represents
 * the selection.
 * @param emphasis when both the leading and trailing content is provided, this parameter
 * determines which content to emphasize by making it larger, darker and bolder.
 * @param trailingIcon the drawable resource that appears on the right side of the row
 * which represents the action user can take.
 * @param trailingIcon a composable icon that appears on the right side of the row.
 * either provide the composable icon or the drawable resource but not both.
 * @param itemClick the action that is taken when the user clicks on the row.
 */
@Composable
fun ListItemType1(itemId: Int,
                  headlineText: String,
                  modifier: Modifier = Modifier,
                  trailingText: String? = null,
                  trailingIcon: ImageVector? = null,
                  emphasis: Set<Emphasis> = setOf(Emphasis.TrailingContent),
                  itemClick: ((Int) -> Unit)? = null) {

    // if trailing text is provided, we need to determine whether to emphasize leading
    // content or the trailing content
    val shouldEmphasizeTrailingContent = if (trailingText != null && emphasis.contains(Emphasis.TrailingContent)) {
        true
    } else {
        false
    }

    var headlineTextColor = defaultText
    var trailingTextColor = secondaryText
    var headlineTextStyle = Typography.bodyLarge
    var trailingTextStyle = Typography.bodyMedium

    if (shouldEmphasizeTrailingContent) {
        headlineTextColor = secondaryText
        trailingTextColor = defaultText
        headlineTextStyle = Typography.bodyMedium
        trailingTextStyle = Typography.bodyLarge
    }

    val listItemModifier: Modifier = if (itemClick != null) {
        Modifier
            .clickable { itemClick(itemId) }
            .then(modifier)
    } else {
        modifier
    }

    ListItem(
        modifier = listItemModifier,
        colors = ListItemDefaults.colors(
            headlineColor = headlineTextColor,
            containerColor = primaryBackground,
            trailingIconColor = defaultIcon,
        ),
        headlineContent = {
            Text(headlineText, style = headlineTextStyle)
        },

        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                if (trailingText != null) {
                    Text(trailingText,
                        color = trailingTextColor,
                        style = trailingTextStyle)
                }

                // spacer is only needed if there is a trailing text AND trailing icon
                if (trailingText != null) {
                    if (trailingIcon != null) {
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }

                if (trailingIcon != null) {
                    Icon(imageVector = trailingIcon,
                        contentDescription = "Icon")
                }
            }
        }
    )
}

@Composable
fun ListItemType2(headlineText: String,
                  trailingText: String? = null) {
    ListItem(
        colors = ListItemDefaults.colors(
            headlineColor = Color.Black,
            containerColor = Color.White
        ),
        headlineContent = {
            Text(headlineText,
                style = Typography.titleLarge)
        },

        trailingContent = {
            if (trailingText != null) {
                Text(
                    trailingText, fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}

@Preview(widthDp = 350, heightDp = 900)
@Composable
fun ListItemTypesPreview() {
    Column(modifier = Modifier.background(Color.White)) {
        ListItemHeader(text = "ListItemType3")
        ListItemType2(headlineText = "Snow", trailingText = null)
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 with trailing @DrawableRes icon")
        ListItemType1(
            itemId = 10,
            headlineText = "Rainbow",
            trailingText = "Clouds",
            trailingIcon = Icons.Default.Clear,
            itemClick = { }
        )
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 with trailing @Composable icon")
        ListItemType1(itemId = 9,
            headlineText = "Rain",
            trailingText = "Storm",
            trailingIcon = Icons.AutoMirrored.Default.KeyboardArrowRight,
            itemClick = { })
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))

        ListItemHeader(text = "ListItemType1 with trailing Clickable Icon")
        ListItemType1(
            modifier = Modifier.padding(end = 0.dp),
            itemId = 93,
            headlineText = "Rain",
            trailingText = "Storm",
            trailingIcon = Icons.AutoMirrored.Default.KeyboardArrowRight,
        )
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 with checkmark icon")
        ListItemType1(itemId = 9,
            headlineText = "Hot and humid",
            trailingIcon = Icons.Default.Check,
            itemClick = { })
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 when trailing text is emphasized")
        ListItemType1(itemId = 98,
            headlineText = "Wind",
            trailingText = "Ice")
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 when leading text is emphasized")
        ListItemType1(itemId = 98,
            headlineText = "Wind",
            trailingText = "Ice",
            emphasis = setOf(Emphasis.LeadingContent)
        )
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //
        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 when trailing text is null")
        ListItemType1(itemId = 27,
            headlineText = "Wind",
            trailingText = null)
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // Section separator //

        Spacer(modifier = Modifier.padding(Dimensions.standardPadding))
        ListItemHeader(text = "ListItemType1 when leading content is emphasized")
        ListItemType1(itemId = 27,
            headlineText = "Coordinates",
            trailingText = "38.3937, -64.9272",
            emphasis = setOf(Emphasis.LeadingContent),
            trailingIcon = Icons.AutoMirrored.Default.KeyboardArrowRight,
        )
        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))
    }
}