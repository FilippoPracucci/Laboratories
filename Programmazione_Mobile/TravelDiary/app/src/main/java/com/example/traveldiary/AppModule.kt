package com.example.traveldiary

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.traveldiary.data.database.TravelDiaryDatabase
import com.example.traveldiary.data.repositories.PlacesRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.traveldiary.data.repositories.SettingsRepository
import com.example.traveldiary.ui.PlacesViewModel
import com.example.traveldiary.ui.screens.addtravel.AddTravelViewModel
import com.example.traveldiary.ui.screens.settings.SettingsViewModel
import org.koin.dsl.module

val Context.datastore by preferencesDataStore("settings")

val appModule = module {
    single { get<Context>().datastore }

    single {
        Room.databaseBuilder(
            get(),
            TravelDiaryDatabase::class.java,
            "travel-diary"
        ).build()
    }

    single { PlacesRepository(get<TravelDiaryDatabase>().placesDAO()) }

    single { SettingsRepository(get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { AddTravelViewModel() }

    viewModel { PlacesViewModel(get()) }
}
