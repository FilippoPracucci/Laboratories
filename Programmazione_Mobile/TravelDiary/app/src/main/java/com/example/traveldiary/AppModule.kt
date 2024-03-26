package com.example.traveldiary

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.traveldiary.data.repositories.SettingsRepository
import com.example.traveldiary.ui.screens.addtravel.AddTravelViewModel
import com.example.traveldiary.ui.screens.settings.SettingsViewModel
import org.koin.dsl.module

val Context.datastore by preferencesDataStore("settings")

val appModule = module {
    single { get<Context>().datastore }

    single { SettingsRepository(get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { AddTravelViewModel() }
}
