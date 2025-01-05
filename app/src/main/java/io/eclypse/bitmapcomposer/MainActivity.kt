package io.eclypse.bitmapcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.eclypse.bitmapcomposer.note.NoteScreen
import io.eclypse.bitmapcomposer.note.NoteViewState
import io.eclypse.bitmapcomposer.note.Coordinate2d
import io.eclypse.bitmapcomposer.note.ImageReference
import io.eclypse.bitmapcomposer.note.Rating
import io.eclypse.bitmapcomposer.ui.theme.BitmapComposerTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteBody = "The Milky Way is a huge collection of stars, dust and gas."

        val initialNote =
            NoteViewState(
                noteTitle = "Milky Way Galaxy",
                initialStateConfigured = true,
                noteId = UUID.randomUUID().toString(),
                noteBody = noteBody,
                rating = Rating.THREE_STAR,
                isInEditMode = true,
                renderForScreenCapture = false,
                coordinate = Coordinate2d(latitude = 33.984818, longitude = -103.65372),
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
                NoteScreen(
                    noteViewState = initialNote,
                    onChangeNoteContents = {},
                    onChangeNoteTitle = {},
                    onDeleteAttachment = {},
                    onSelectGrowthStage = {},
                    onAttachNewPhoto = {},
                    onRequestToSelectNoteLocation = {},
                    onShareNote = { screenshot ->

                    },
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
            "like a spinning pinwheel. The Sun is located on one of the spiral arms, about 25,000 "

    val previewNote =
        NoteViewState(
            noteTitle = "Milky Way Galaxy",
            initialStateConfigured = true,
            noteId = UUID.randomUUID().toString(),
            noteBody = fancyNoteBody,
            rating = Rating.FOUR_STAR,
            isInEditMode = false,
            renderForScreenCapture = false,
            coordinate = Coordinate2d(latitude = 33.984818, longitude = -103.65372),
            associatedField = null,
            attachments = listOf(
                ImageReference.ByDrawable(R.drawable.forest),
                ImageReference.ByDrawable( R.drawable.lotus),
                ImageReference.ByDrawable( R.drawable.tiles),
            ),
        )

    BitmapComposerTheme {
        NoteScreen(
            noteViewState = previewNote,
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