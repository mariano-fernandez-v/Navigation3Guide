package com.plcoding.navigation3guide.di

import com.plcoding.navigation3guide.note.NoteDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::NoteDetailViewModel)
}