package io.eclypse.bitmapcomposer.ui

import androidx.annotation.FloatRange

data class Coordinate2d(
    @FloatRange(from = -90.0, to = 90.0) val latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) val longitude: Double,
) {
    constructor() : this(0.0, 0.0)

    fun prettyPrinted(precision: Int = 4): String = "${formatLat(precision)}, ${formatLon(precision)}"

    fun formatLat(precision: Int = 6): String = "%.${precision}f".format(latitude)

    fun formatLon(precision: Int = 6): String = "%.${precision}f".format(longitude)
}