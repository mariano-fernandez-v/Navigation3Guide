package com.plcoding.navigation3guide.navigation.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.plcoding.navigation3guide.navigation.Destination
import com.plcoding.navigation3guide.profile.Profile

@Composable
fun HomeGraph() {
    val backStack = rememberNavBackStack<NavKey>(Destination.Home)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Destination.Home> { entry ->
                Profile(
                    name = "Cucuruxo",
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }

            entry<Destination.NameEditDialog> { entry ->
                Text("Name Edit Dialog")

            }

            entry<Destination.NameEditScreen> { entry ->
                Text("Name Edit Screen")
            }
        }
    )
}