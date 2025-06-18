package com.plcoding.navigation3guide.note

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val color: Long
) : Parcelable

val sampleNotes = List(100) {
    Note(
        id = it,
        title = "Note $it",
        content = "Content $it",
        color = Color(Random.nextLong(0xFFFFFFFF)).copy(alpha = 0.5f).toColorLong()
    )
}