package com.plcoding.navigation3guide.note

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val colorValue: ULong
) : Parcelable {
    val color: Color
        get() = ColorSerializer.toColor(colorValue)
}

val sampleNotes = List(100) {
    val color = randomColor(alpha = 0.5f)
    Note(
        id = it,
        title = "Note $it",
        content = "Content $it",
        colorValue = ColorSerializer.fromColor(color)
    )
}

fun randomColor(alpha: Float = 0.5f): Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    val alphaInt = (alpha * 255).toInt()
    return Color(red, green, blue, alphaInt)
}

object ColorSerializer {
    fun fromColor(color: Color): ULong = color.value

    fun toColor(value: ULong): Color = Color(value)
}
