package com.plcoding.navigation3guide.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.plcoding.navigation3guide.navigation.graphs.HomeGraph
import com.plcoding.navigation3guide.navigation.graphs.NoteGraph
import com.plcoding.navigation3guide.navigation.saver.rememberMutableStateListOf


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    var currentBottomBarScreen: NavKey by rememberSaveable(stateSaver = BottomBarScreenSaver) {
        mutableStateOf(Destinations.HomeGraph)
    }

    val backStack = rememberNavBackStack<NavKey>(Destinations.HomeGraph)
    val stateHolder = rememberSaveableStateHolder()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                currentDestination = currentBottomBarScreen,
                onDestinationClicked = { destination ->
                    if (backStack.lastOrNull() != destination) {
                        backStack.removeLastOrNull()
                        backStack.add(destination)
                        currentBottomBarScreen = destination
                    }
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = modifier.padding(top = paddingValues.calculateTopPadding()),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(stateHolder),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Destinations.NoteGraph> { entry ->
                    stateHolder.SaveableStateProvider(entry.title) {
                        NoteGraph()
                    }
                }

                entry<Destinations.HomeGraph> { entry ->
                    stateHolder.SaveableStateProvider(entry.title) {
                        HomeGraph()
                    }
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
        Destinations.HomeGraph,
    ),
    Item(
        1,
        "List",
        Icons.Default.List,
        Destinations.NoteGraph,
    ),
)

val BottomBarScreenSaver = Saver<NavKey, String>(
    save = { it::class.simpleName ?: "Uknown" },
    restore = {
        when (it) {
            Destinations.HomeGraph::class.simpleName -> Destinations.HomeGraph
            Destinations.NoteGraph::class.simpleName -> Destinations.NoteGraph
            else -> Destinations.HomeGraph
        }
    }
)
