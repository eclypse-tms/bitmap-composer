package io.eclypse.bitmapcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.eclypse.bitmapcomposer.ui.AddNoteContents
import io.eclypse.bitmapcomposer.ui.AddNoteViewState
import io.eclypse.bitmapcomposer.ui.Coordinate2d
import io.eclypse.bitmapcomposer.ui.ImageReference
import io.eclypse.bitmapcomposer.ui.Rating
import io.eclypse.bitmapcomposer.ui.theme.BitmapComposerTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fancyNoteBody = "Billions of years ago, when the solar system was first forming, there " +
                "were many more mini planets than the planets we know today. Astronomers believe " +
                "that at one point, Earth collided with a mini planet called Theia, which caused our " +
                "planet to tilt by 23 degrees. Or as Andrew Fraknoi, astronomer and professor at the " +
                "University of San Francisco’s Fromm Institute, said: “The Earth was in a traffic " +
                "accident and has never been able to straighten out."

        val previewNote =
            AddNoteViewState(
                noteTitle = "Theia Collision",
                initialStateConfigured = true,
                noteId = UUID.randomUUID().toString(),
                noteBody = fancyNoteBody,
                rating = Rating.FOUR_STAR,
                isInEditMode = false,
                renderForScreenCapture = false,
                coordinate = Coordinate2d(latitude = 41.984818, longitude = -93.65372),
                associatedField = null,
                attachments = listOf(
                    ImageReference.ByDrawable(R.drawable.forest),
                    ImageReference.ByDrawable(R.drawable.lotus),
                    ImageReference.ByDrawable(R.drawable.tiles),
                ),
            )

        enableEdgeToEdge()
        setContent {
            BitmapComposerTheme {
                AddNoteContents(
                    addNoteViewState = previewNote,
                    onChangeNoteContents = {},
                    onChangeNoteTitle = {},
                    onDeleteAttachment = {},
                    onSelectGrowthStage = {},
                    onAttachNewPhoto = {},
                    onRequestToSelectNoteLocation = {},
                    onShareNote = {},
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val fancyNoteBody = "The Milky Way is a huge collection of stars, dust and gas. It’s called " +
            "a spiral galaxy because if you could view it from the top or bottom, it would look " +
            "like a spinning pinwheel. The Sun is located on one of the spiral arms, about 25,000 " +
            "light-years away from the center of the galaxy. Even if you could travel at the speed " +
            "of light (300,000 kilometers, or 186,000 miles, per second), it would take you about " +
            "25,000 years to reach the middle of the Milky Way. The Milky Way gets its name from a " +
            "Greek myth about the goddess Hera who sprayed milk across the sky. In other parts of " +
            "the world, our galaxy goes by other names. In China it’s called the “Silver River,” " +
            "and in the Kalahari Desert in Southern Africa, it’s called the “Backbone of Night."

    val previewNote =
        AddNoteViewState(
            noteTitle = "Milky Way Galaxy",
            initialStateConfigured = true,
            noteId = UUID.randomUUID().toString(),
            noteBody = fancyNoteBody,
            rating = Rating.FOUR_STAR,
            isInEditMode = false,
            renderForScreenCapture = false,
            coordinate = Coordinate2d(latitude = 41.984818, longitude = -93.65372),
            associatedField = null,
            attachments = listOf(
                ImageReference.ByDrawable(R.drawable.forest),
                ImageReference.ByDrawable( R.drawable.lotus),
                ImageReference.ByDrawable( R.drawable.tiles),
            ),
        )

    BitmapComposerTheme {
        AddNoteContents(
            addNoteViewState = previewNote,
            onChangeNoteContents = {},
            onChangeNoteTitle = {},
            onDeleteAttachment = {},
            onSelectGrowthStage = {},
            onAttachNewPhoto = {},
            onRequestToSelectNoteLocation = {},
            onShareNote = {},
        )
    }
}