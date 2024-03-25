package com.example.themetoggle

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.themetoggle.data.repositories.ThemeRepository
import com.example.themetoggle.ui.screens.ThemeToggleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.datastore by preferencesDataStore("theme")

val appModule = module {
    // DataStore
    single { get<Context>().datastore }

    // Repository
    single { ThemeRepository(get()) }

    // ViewModel
    viewModel { ThemeToggleViewModel(get()) }
}