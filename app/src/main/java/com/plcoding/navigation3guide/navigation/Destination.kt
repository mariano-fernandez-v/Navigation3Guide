package com.plcoding.navigation3guide.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    val title : String

    //Home
    @Serializable
    data object HomeGraph : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object Home : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object NameEditDialog : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object NameEditScreen : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
    
    //Notes
    @Serializable
    data object NoteGraph : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
    
    @Serializable
    data object NoteListScreen : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data class NoteDetailScreen(val id: Int) : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    //Settings

    @Serializable
    data object Settings : Destination{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
}
