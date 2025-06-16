package com.plcoding.navigation3guide.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryWrapper
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.plcoding.navigation3guide.note.NoteDetailScreenUi
import com.plcoding.navigation3guide.note.NoteListScreenUi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Map.entry


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack<NavKey>(Destinations.NoteListScreen)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                currentDestination = backStack.last(),
                onDestinationClicked = { destination ->
                    backStack.removeLastOrNull()
                    backStack.add(destination)
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = modifier.padding(paddingValues),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
                rememberSceneSetupNavEntryDecorator()
            ),
            sceneStrategy = TwoPaneSceneStrategy(),
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

                entry<Destinations.Home> { entry ->
                    Profile(
                        name = "Cucuruxo",
                        onNavigate = { destination ->
                            backStack.add(destination)
                        }
                    )
                }

                entry<Destinations.NameEditDialog> { entry ->
                    Text("Name Edit Dialog")

                }

                entry<Destinations.NameEditScreen> { entry ->
                    Text("Name Edit Screen")
                }
            }
        )
    }
}

@Composable
private fun BottomBar(
    currentDestination: NavKey,
    onDestinationClicked: (NavKey) -> Unit
) {

    NavigationBar {
        Items.forEach { item ->
            NavigationBarItem(
                selected = item.destination == currentDestination,
                onClick = {
                    onDestinationClicked(item.destination)
                },
                label = {
                    Text(item.title)
                },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
            )
        }
    }
}

private data class Item(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val destination: NavKey,
)

private val Items = listOf(
    Item(
        0,
        "Home",
        Icons.Default.Home,
        Destinations.Home,
    ),
    Item(
        1,
        "List",
        Icons.Default.List,
        Destinations.NoteListScreen,
    ),
)


internal sealed interface Destinations : NavKey {
    @Serializable
    data object Home : NavKey

    @Serializable
    data object NoteListScreen : NavKey

    @Serializable
    data class NoteDetailScreen(val id: Int) : NavKey

    @Serializable
    data object NameEditDialog : NavKey

    @Serializable
    data object NameEditScreen : NavKey

}

@Composable
private fun Profile(
    name: String,
    onNavigate: (NavKey) -> Unit,
) {
    var times by rememberSaveable { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Profile")

        Text("Name: $name, changed: $times")

        OutlinedButton(
            onClick = {
                times++
                onNavigate(Destinations.NameEditDialog)
            },
        ) {
            Text("Change Name Dialog")
        }
        OutlinedButton(
            onClick = {
                times++
                onNavigate(Destinations.NameEditScreen)
            },
        ) {
            Text("Change Name Screen")
        }
    }
}