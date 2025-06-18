package com.plcoding.navigation3guide.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.plcoding.navigation3guide.navigation.Destination

@Composable
fun Profile(
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
                onNavigate(Destination.NameEditDialog)
            },
        ) {
            Text("Change Name Dialog")
        }
        OutlinedButton(
            onClick = {
                times++
                onNavigate(Destination.NameEditScreen)
            },
        ) {
            Text("Change Name Screen")
        }
    }
}