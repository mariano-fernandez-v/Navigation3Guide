package com.plcoding.navigation3guide.navigation.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.plcoding.navigation3guide.navigation.Destination
import com.plcoding.navigation3guide.navigation.scenes.TwoPaneScene
import com.plcoding.navigation3guide.navigation.scenes.TwoPaneSceneStrategy
import com.plcoding.navigation3guide.note.NoteDetailScreenUi
import com.plcoding.navigation3guide.note.NoteListScreenUi
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteGraph() {
    val backStack = rememberNavBackStack<Destination>(Destination.NoteListScreen)
    val stateHolder = rememberSaveableStateHolder()

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        sceneStrategy = TwoPaneSceneStrategy(),
        entryProvider = entryProvider {
            entry<Destination.NoteListScreen>(
                metadata = TwoPaneScene.twoPane()
            ) {
                stateHolder.SaveableStateProvider(it.title) {
                    NoteListScreenUi(
                        onNoteClick = { noteId ->
                            if (backStack.lastOrNull() is Destination.NoteDetailScreen){
                                backStack.removeLastOrNull()
                            }
                            backStack.add(Destination.NoteDetailScreen(noteId))
                        })
                }
            }

            entry<Destination.NoteDetailScreen>(
                metadata = TwoPaneScene.twoPane()
            ) { entry ->
                stateHolder.SaveableStateProvider(entry.id) {
                    NoteDetailScreenUi(
                        viewModel = koinViewModel {
                            parametersOf(entry.id)
                        })
                }
            }
        }
    )
}
