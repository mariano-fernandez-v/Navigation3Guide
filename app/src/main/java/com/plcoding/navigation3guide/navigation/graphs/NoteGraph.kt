package com.plcoding.navigation3guide.navigation.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.plcoding.navigation3guide.navigation.Destinations
import com.plcoding.navigation3guide.note.NoteDetailScreenUi
import com.plcoding.navigation3guide.note.NoteListScreenUi
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteGraph() {
    val backStack = rememberNavBackStack<NavKey>(Destinations.NoteListScreen)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Destinations.NoteListScreen> { entry ->
                NoteListScreenUi(
                    onNoteClick = { noteId ->
                        backStack.add(Destinations.NoteDetailScreen(noteId))
                    }
                )
            }

            entry<Destinations.NoteDetailScreen> { entry ->
                    NoteDetailScreenUi(
                        viewModel = koinViewModel {
                            parametersOf(entry.id)
                        }
                    )
            }
        }
    )
}
