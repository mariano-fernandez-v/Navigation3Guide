package com.plcoding.navigation3guide.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destinations : NavKey {
    val title : String

    //Home
    @Serializable
    data object HomeGraph : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object Home : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object NameEditDialog : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data object NameEditScreen : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
    
    //Notes
    @Serializable
    data object NoteGraph : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
    
    @Serializable
    data object NoteListScreen : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }

    @Serializable
    data class NoteDetailScreen(val id: Int) : Destinations{
        override val title: String
            get() = this::class.simpleName.orEmpty()
    }
}
