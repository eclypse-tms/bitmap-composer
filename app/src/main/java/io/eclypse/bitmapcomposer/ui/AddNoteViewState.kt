package io.eclypse.bitmapcomposer.ui

data class AddNoteViewState(
    val noteId: String,

    val noteTitle: String = "",

    val noteBody: String = "",

    val associatedField: String? = null,


    /**
     * Optional coordinates for this note. Might be null if the user has not provided any field or
     * picked a location.
     */
    val coordinate: Coordinate2d? = null,

    /**
     * List of attachment URLs that are associated with this note. This list is used to display
     * the thumbnails of the attachments.
     */
    val attachments: List<ImageReference> = emptyList(),

    /**
     * Indicates whether the form is in edit mode or not. If the user clicks on an existing
     * note, initially user is taken to the view-only version of the note where certain
     * elements that allows the user to save or modify the note is hidden.
     */
    val isInEditMode: Boolean = false,

    /**
     * When this value is true, the elements in [AddNoteScreen] will be rendered in a way that is
     * appropriate for sharing. Specifically Carousel will be hidden and replaced by larger
     * images at the end of the note.
     */
    val renderForScreenCapture: Boolean = false,

    /**
     * Prevents Composable functions from calling onReceive(intent: Intent.InitialState) multiple times.
     *
     * This happens because we are calling onReceive(intent:) with Intent.InitialState in the
     * [AddNoteScreen] composable in [androidx.compose.runtime.LaunchedEffect] block. The problem
     * is that when another screen is pushed on to the navigation stack, AddNoteScreen leaves
     * the composition and everything is canceled/reset. However, when the navigation stack is popped
     * back, we enter into composition again and the LaunchedEffect block runs one more time potentially
     * resetting the edits that were made.
     */
    val initialStateConfigured: Boolean = false,

    /**
     * If the note sync process fails, this will tell the UI to let the user know.
     */
    val hasSyncError: Boolean = false,

    val rating: Rating? = null,
) {

    /**
     * controls whether to show certain elements that are not relevant for screen printing
     * such camera button, save-cancel-delete buttons.
     */
    val showInteractiveElements: Boolean
        get() {
            return if (renderForScreenCapture) {
                false
            } else {
                isInEditMode
            }
        }

    /**
     * controls whether to show the X button on the attachment thumbnails.
     * we don't show the X delete button when rendering for screenshot
     * or when the form is in view mode
     */
    val canDeleteAttachedMedia: Boolean
        get() {
            return if (renderForScreenCapture) {
                false
            } else {
                isInEditMode
            }
        }

    val pictureMode: PictureMode
        get() {
            val firstAttachment = attachments.firstOrNull() ?: return PictureMode.BITMAP
            return when (firstAttachment) {
                is ImageReference.ByUrl -> PictureMode.URL
                is ImageReference.ByBitmap -> PictureMode.BITMAP
                is ImageReference.ByDrawable -> PictureMode.DRAWABLE
            }
        }
}