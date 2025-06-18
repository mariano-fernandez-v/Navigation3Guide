package com.plcoding.navigation3guide.note

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun NoteListScreenUi(
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var sampleNotesItems by rememberSaveable { mutableStateOf<List<Note>?>(null) }

    LaunchedEffect(Unit) {
        delay(2000)
        sampleNotesItems = sampleNotes
    }

    sampleNotesItems?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            items(sampleNotesItems.orEmpty()) { note ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(note.color))
                        .clickable {
                            onNoteClick(note.id)
                        }
                ) {
                    Text(
                        text = note.title,
                        fontSize = 18.sp
                    )
                    Text(
                        text = note.content,
                    )
                }
            }
        }
    } ?: LoaderScreen()
}

@Composable
fun LoaderScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading...")
    }
}