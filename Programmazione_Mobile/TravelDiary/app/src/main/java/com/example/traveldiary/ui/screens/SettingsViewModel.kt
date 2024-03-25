package com.example.traveldiary.ui.screens

import androidx.lifecycle.ViewModel
import com.example.traveldiary.data.repositories.SettingsRepository

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    init {
        println("SettingsViewModel created")
    }

    fun setUsername() {

    }
}