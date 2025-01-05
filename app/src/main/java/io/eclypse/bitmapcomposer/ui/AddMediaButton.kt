package io.eclypse.bitmapcomposer.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.eclypse.bitmapcomposer.ui.theme.Purple40

@Composable
fun AddMediaButton(onClick: () -> Unit) {
    ConstraintLayout(
        modifier =
        Modifier
            .size(75.dp)
            .padding(top = 8.dp, end = 12.dp),
    ) {
        val (takeNewPicture) = createRefs()

        IconButton(
            modifier =
            Modifier
                .border(
                    1.dp,
                    Purple40,
                    shape = RoundedCornerShape(4.dp),
                )
                .constrainAs(takeNewPicture) {
                    centerTo(parent)
                },
            colors =
            IconButtonDefaults.iconButtonColors(
                contentColor = Purple40,
            ),
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add a photo",
            )
        }
    }
}