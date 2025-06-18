package com.plcoding.navigation3guide.note.viewmodel

import androidx.lifecycle.ViewModel
import com.plcoding.navigation3guide.note.sampleNotes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteDetailViewModel(
    private val noteId: Int
): ViewModel() {

    private val _noteState = MutableStateFlow(
        sampleNotes.first { it.id == noteId }
    )
    val noteState = _noteState.asStateFlow()
}