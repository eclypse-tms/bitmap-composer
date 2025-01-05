package io.eclypse.bitmapcomposer.note

import android.app.Activity
import io.eclypse.bitmapcomposer.ui.theme.Emphasis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.eclypse.bitmapcomposer.R
import io.eclypse.bitmapcomposer.ui.theme.BitmapComposerTheme
import io.eclypse.bitmapcomposer.ui.theme.Dimensions
import io.eclypse.bitmapcomposer.ui.theme.ListItemHeader
import io.eclypse.bitmapcomposer.ui.theme.ListItemType1
import io.eclypse.bitmapcomposer.ui.theme.ListItemType2
import io.eclypse.bitmapcomposer.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * State hoisted Add or Edit Note Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteViewState: NoteViewState,
    onChangeNoteTitle: (String) -> Unit,
    onChangeNoteContents: (String) -> Unit,
    onDeleteAttachment: (Int) -> Unit,
    onSelectGrowthStage: (Rating?) -> Unit,
    onAttachNewPhoto: () -> Unit,
    onRequestToSelectNoteLocation: (Coordinate2d?) -> Unit,
    onShareNote: (Screenshot) -> Unit,
    modifier: Modifier = Modifier,
) {

    val currentContext = LocalContext.current
    val screenDensity: Density = LocalDensity.current

    // this should only return true for previews
    val compositionCoroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Note",
                    )
                },
                navigationIcon = { },
                actions = {
                    IconButton(
                        onClick = {
                            compositionCoroutineScope.launch {
                                val screenshot = Screenshot(
                                    currentActivity = currentContext as Activity,
                                    screenDensity = screenDensity,
                                    composableView = {
                                        val readyForScreenshotState =
                                            noteViewState.copy(renderForScreenCapture = true)
                                        BitmapComposerTheme {
                                            NoteScreen(
                                                noteViewState = readyForScreenshotState,
                                                onDeleteAttachment = { },
                                                onSelectGrowthStage = { },
                                                onAttachNewPhoto = { },
                                                onRequestToSelectNoteLocation = { },
                                                onChangeNoteTitle = { },
                                                onChangeNoteContents = { },
                                                onShareNote = { },
                                            )
                                        }
                                    })

                                onShareNote(screenshot)
                            }
                        },
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Localized Description")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .then(modifier),
        ) {
            if (!noteViewState.renderForScreenCapture) {
                // only show the carousel when we are rendering for the normal view
                RenderCarouselSection(noteViewState = noteViewState)
            }

            Column(modifier = Modifier.padding(Dimensions.standardPadding)) {

                ListItemHeader("Title")
                TextField(
                    value = noteViewState.noteTitle,
                    onValueChange = onChangeNoteTitle,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(
                    modifier = Modifier
                        .height(Dimensions.halfPadding)
                )

                ListItemHeader("Contents")

                TextField(
                    modifier = Modifier.heightIn(min = 80.dp)
                        .fillMaxWidth(),
                    value = noteViewState.noteBody,
                    onValueChange = onChangeNoteContents,
                )

                Spacer(
                    modifier =
                    Modifier
                        .height(Dimensions.doublePadding)
                        .fillMaxWidth(),
                )

                RenderAttachMediaSection(
                    renderForScreenshot = noteViewState.renderForScreenCapture,
                    noteViewState = noteViewState,
                    onAttachNewPhoto = onAttachNewPhoto,
                    onDeleteAttachment = onDeleteAttachment,
                )

                Spacer(
                    modifier =
                    Modifier
                        .height(Dimensions.doublePadding)
                        .fillMaxWidth(),
                )

                RenderRatingSection(
                    noteViewState = noteViewState,
                    onSelectRating = onSelectGrowthStage,
                )


            }

            Spacer(
                modifier =
                Modifier
                    .height(Dimensions.standardPadding)
                    .fillMaxWidth(),
            )

            RenderNoteInfoSection(
                noteViewState = noteViewState,
                onRequestToSelectCoordinates = onRequestToSelectNoteLocation,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RenderCarouselSection(
    noteViewState: NoteViewState,
) {
    val currentConfiguration = LocalConfiguration.current

    // we are intentionally not remembering the carousel state because we want the carousel
    // to update itself automatically every time the number of attachments changes
    val carouselState =
        CarouselState {
            noteViewState.attachments.count()
        }

    HorizontalUncontainedCarousel(
        state = carouselState,
        itemWidth = currentConfiguration.screenWidthDp.dp,
        modifier = Modifier.height(200.dp),
        itemSpacing = 8.dp,
        flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(carouselState),
    ) { index ->
        NoteCarouselItem(attachment = noteViewState.attachments[index])
    }

    Spacer(
        modifier =
        Modifier
            .height(8.dp)
            .fillMaxWidth(),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        val totalNumberOfImagesInCarousel = noteViewState.attachments.size
        for (index in 0 until totalNumberOfImagesInCarousel) {
            Box(
                modifier =
                Modifier
                    .size(8.dp)
                    .background(Color(0xFFD9D9D9), CircleShape),
            )
            if (index != totalNumberOfImagesInCarousel - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
private fun RenderNoteInfoSection(
    noteViewState: NoteViewState,
    onRequestToSelectCoordinates: (Coordinate2d?) -> Unit,
) {
    Column {
        ListItemType2(headlineText = "Note Information")

        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        val trailingIcon: ImageVector? =
            if (noteViewState.isInEditMode) {
                Icons.AutoMirrored.Default.KeyboardArrowRight
            } else {
                null
            }

        val coordinateSelectionItemClick: ((Int) -> Unit)? =
            if (noteViewState.isInEditMode) {
                { onRequestToSelectCoordinates(noteViewState.coordinate) }
            } else {
                null
            }

        ListItemType1(
            itemId = 1,
            headlineText = "Location",
            trailingText = noteViewState.coordinate?.prettyPrinted() ?: "n/a",
            trailingIcon = trailingIcon,
            emphasis = setOf(Emphasis.LeadingContent),
            itemClick = coordinateSelectionItemClick,
        )

        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        ListItemType1(
            itemId = 1,
            headlineText = "Author",
            trailingText = "Robert Frost",
            emphasis = setOf(Emphasis.LeadingContent),
            itemClick = coordinateSelectionItemClick,
        )

        HorizontalDivider(Modifier.padding(start = Dimensions.standardPadding))

        // overscroll
        Spacer(
            modifier =
            Modifier
                .height(Dimensions.doublePadding)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun RenderRatingSection(
    noteViewState: NoteViewState,
    onSelectRating: (Rating) -> Unit,
) {
    ListItemHeader("Rating")

    Row(Modifier.selectableGroup(),
            verticalAlignment = Alignment.CenterVertically,) {
        Text("1   ")
        for (eachRating in Rating.entries) {
            val shouldBeSelected = (noteViewState.rating?.ordinal ?: -1) >= eachRating.ordinal

            RadioButton(
                selected = shouldBeSelected,
                onClick = { onSelectRating(eachRating) },
                enabled = noteViewState.isInEditMode,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Purple40,
                ),
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
        }
        Text("   5")
    }
}

@Composable
private fun RenderAttachMediaSection(
    renderForScreenshot: Boolean,
    noteViewState: NoteViewState,
    onAttachNewPhoto: () -> Unit,
    onDeleteAttachment: (Int) -> Unit,
) {
    ListItemHeader(
        text = "Attach Photos",
    )

    val cellSize: GridCells
    val thumbNailModifier: Modifier
    val maxAttachmentSectionHeight: Dp
    if (renderForScreenshot) {
        cellSize = GridCells.Fixed(1)
        thumbNailModifier = Modifier.padding(4.dp)
        maxAttachmentSectionHeight = 3000.dp
    } else {
        cellSize = GridCells.Adaptive(120.dp)
        thumbNailModifier = Modifier.padding(4.dp)
        maxAttachmentSectionHeight = 600.dp
    }

    if (noteViewState.renderForScreenCapture) {
        // when rendering for the screenshot - we want the images to take up the entire width
        Column {
            noteViewState.attachments.forEachIndexed { index, attachment ->
                Thumbnail(
                    modifier = thumbNailModifier,
                    index = index,
                    imageReference = attachment,
                    isDeletable = noteViewState.canDeleteAttachedMedia,
                    onDeleteAttachment = onDeleteAttachment,
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = cellSize,
            userScrollEnabled = false,
            modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(0.dp, maxAttachmentSectionHeight),
        ) {
            item(key = "addMediaButton") {
                if (noteViewState.showInteractiveElements) {
                    // the first entry in the media urls is always empty because we are using
                    // that as a placeholder for attach media button when in edit mode
                    AddMediaButton(onClick = onAttachNewPhoto)
                }
            }

            itemsIndexed(noteViewState.attachments) { index, attachment ->
                Thumbnail(
                    modifier = thumbNailModifier,
                    index = index,
                    imageReference = attachment,
                    isDeletable = noteViewState.canDeleteAttachedMedia,
                    onDeleteAttachment = onDeleteAttachment,
                )
            }
        }
    }
}

@Preview(heightDp = 2000)
@Composable
fun AddNoteScreenPreview() {

    val noteBody = "The Milky Way is a huge collection of stars, dust and gas."

    val previewNote =
        NoteViewState(
            noteTitle = "Milky Way Galaxy",
            initialStateConfigured = true,
            noteId = UUID.randomUUID().toString(),
            noteBody = noteBody,
            rating = Rating.THREE_STAR,
            isInEditMode = true,
            renderForScreenCapture = true,
            coordinate = Coordinate2d(latitude = 33.984818, longitude = -103.65372),
            associatedField = null,
            attachments = listOf(
                ImageReference.ByDrawable(R.drawable.forest),
                ImageReference.ByDrawable(R.drawable.lotus),
                ImageReference.ByDrawable(R.drawable.tiles),
            ),
        )

    BitmapComposerTheme {
        NoteScreen(
            noteViewState = previewNote,
            onChangeNoteTitle = {},
            onChangeNoteContents = {},
            onDeleteAttachment = {},
            onSelectGrowthStage = {},
            onAttachNewPhoto = {},
            onRequestToSelectNoteLocation = {},
            onShareNote = {},
        )
    }
}